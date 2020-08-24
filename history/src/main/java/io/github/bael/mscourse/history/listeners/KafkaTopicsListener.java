package io.github.bael.mscourse.history.listeners;

import io.github.bael.mscourse.outbox.service.InboxProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicsListener {
    private final InboxProcessor inboxProcessor;

    @SneakyThrows
    @KafkaListener(topics = "inventory")
    public void listenInventory(String message) {
        log.info("Поступило сообщение {} из топика склада. ", message);
        inboxProcessor.process(message);
    }

    @SneakyThrows
    @KafkaListener(topics = "orders")
    public void listenOrders(String message) {
        log.info("Поступило сообщение {} из топика заказов. ", message);
        inboxProcessor.process(message);
    }

    @SneakyThrows
    @KafkaListener(topics = "accounting")
    public void listenAccounting(String message) {
        log.info("Поступило сообщение {} из топика расчетов. ", message);
        inboxProcessor.process(message);
    }
}
    

