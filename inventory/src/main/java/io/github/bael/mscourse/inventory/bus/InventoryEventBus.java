package io.github.bael.mscourse.inventory.bus;

import io.github.bael.mscourse.outbox.service.Outbox;
import io.github.bael.mscourse.shopdto.v1.event.OrderDeliveredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryEventBus {

    private final Outbox outbox;

    public void sendOrderIsDeliveredEvent(String orderCode, String customerCode, LocalDateTime deliveryDate) {
        OrderDeliveredEvent orderDeliveredEvent = new OrderDeliveredEvent();
        orderDeliveredEvent.setCustomerCode(customerCode);
        orderDeliveredEvent.setOrderCode(orderCode);
        orderDeliveredEvent.setDeliveredOn(deliveryDate);
        outbox.addMessage(orderDeliveredEvent, "inventory", OrderDeliveredEvent.class.getCanonicalName());
    }
}
