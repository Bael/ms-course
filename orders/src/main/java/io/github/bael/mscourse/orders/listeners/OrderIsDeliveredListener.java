package io.github.bael.mscourse.orders.listeners;

import io.github.bael.mscourse.orders.service.OrderService;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderDeliveredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderIsDeliveredListener implements OutboxEventListener<OrderDeliveredEvent> {
    @Override
    public boolean supports(String s) {
        return s.equals(OrderDeliveredEvent.class.getCanonicalName());
    }

    private final OrderService orderService;

    @Override
    public void handle(OrderDeliveredEvent event) {
        orderService.markOrderDelivered(event.getOrderCode(), event.getCustomerCode(), event.getDeliveredOn());

    }
}
