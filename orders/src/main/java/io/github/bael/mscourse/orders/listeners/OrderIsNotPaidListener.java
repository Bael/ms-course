package io.github.bael.mscourse.orders.listeners;

import io.github.bael.mscourse.orders.service.OrderService;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.shopdto.v1.event.OrderDeliveredEvent;
import io.github.bael.mscourse.shopdto.v1.event.OrderNotPaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderIsNotPaidListener implements OutboxEventListener<OrderNotPaidEvent> {
    @Override
    public boolean supports(String s) {
        return s.equals(OrderNotPaidEvent.class.getCanonicalName());
    }

    private final OrderService orderService;

    @Override
    public void handle(OrderNotPaidEvent event) {
        orderService.markOrderUnpaid(event.getOrderCode(), event.getCustomerCode(), event.getOverdueOn());

    }
}
