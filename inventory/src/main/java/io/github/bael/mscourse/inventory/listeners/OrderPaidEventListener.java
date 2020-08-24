package io.github.bael.mscourse.inventory.listeners;

import io.github.bael.mscourse.inventory.api.InventoryApi;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class OrderPaidEventListener implements OutboxEventListener<OrderPaidEvent> {
    @Override
    public boolean supports(String s) {
        return OrderPaidEvent.class.getCanonicalName().equals(s);
    }

    private final InventoryApi inventoryApi;
    @Override
    @Transactional
    public void handle(OrderPaidEvent orderPaidEvent) {
        inventoryApi.shipOrder(orderPaidEvent.getOrderCode());
    }
}
