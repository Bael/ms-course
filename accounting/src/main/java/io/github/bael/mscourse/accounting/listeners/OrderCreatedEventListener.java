package io.github.bael.mscourse.accounting.listeners;

import io.github.bael.mscourse.accounting.service.AccountingAPI;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedEventListener implements OutboxEventListener<OrderCreatedEvent> {
    private final AccountingAPI accountingAPI;
    @Override
    public boolean supports(String type) {
        return type.equals(OrderCreatedEvent.class.getCanonicalName());
    }

    @Override
    public void handle(OrderCreatedEvent event) {
            log.info("Создаем начисление на сумму {} для заказа {}" , event.getTotalAmount(), event.getOrderCode());
            accountingAPI.chargeOrder(event.getTotalAmount(), event.getOrderDate(), event.getCustomerCode(), event.getOrderCode());
    }
}
