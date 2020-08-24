package io.github.bael.mscourse.inventory.listeners;

import io.github.bael.mscourse.inventory.api.InventoryApi;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderNotPaidEvent;
import io.github.bael.mscourse.shopdto.v1.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderNotPaidEventListener implements OutboxEventListener<OrderNotPaidEvent> {
    @Override
    public boolean supports(String s) {
        return OrderNotPaidEvent.class.getCanonicalName().equals(s);
    }

    private final InventoryApi inventoryApi;
    @Override
    @Transactional
    public void handle(OrderNotPaidEvent orderNotPaidEvent) {
        inventoryApi.freeOrder(orderNotPaidEvent.getOrderCode());
    }
}
