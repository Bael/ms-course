package io.github.bael.mscourse.accounting.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bael.mscourse.accounting.service.AccountingAPI;
import io.github.bael.mscourse.outbox.service.InboxService;
import io.github.bael.mscourse.outbox.service.TransportMessage;
import io.github.bael.mscourse.shopdto.v1.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {
    private final ObjectMapper mapper;
    private final AccountingAPI accountingAPI;
    private final InboxService inboxService;


    @SneakyThrows
    @KafkaListener(topics = "orders")
    public void listen(String message) {
        log.info("Поступило сообщение " + message);
        TransportMessage transportMessage = mapper.readValue(message, TransportMessage.class);
        if (inboxService.registerInbox(transportMessage.getMessageId())) {
            log.info("Сообщение ранее не приходило. Обрабатываем.");
            Class<?> aClass = Class.forName(transportMessage.getType());
            Object payload = mapper.readValue(transportMessage.getPayload(), aClass);

            if (transportMessage.getType().equals(OrderCreatedEvent.class.getCanonicalName())) {
                log.info("Cообщение определено как сообщение о создании заказа " + message);
                OrderCreatedEvent event = (OrderCreatedEvent) payload;
                handleOrderCreatedEvent(event);
            } else {

            }

        } else {
            log.warn("Сообщение уже было принято ранее! Пропуск обработки.");
        }
        
    }
    
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Создаем начисление на сумму {} для заказа {}" , event.getTotalAmount(), event.getOrderCode());
        accountingAPI.chargeOrder(event.getTotalAmount(), null, event.getCustomerCode(), event.getOrderCode());
        
    }
}
