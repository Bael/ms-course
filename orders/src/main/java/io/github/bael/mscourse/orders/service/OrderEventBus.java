package io.github.bael.mscourse.orders.service;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.entity.OrderLine;
import io.github.bael.mscourse.outbox.service.Outbox;
import io.github.bael.mscourse.shopdto.v1.ProductRentRequest;
import io.github.bael.mscourse.shopdto.v1.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderEventBus {
    private final Outbox outbox;
    public void sendOrderCreatedEvent(Order order, List<OrderLine> lines) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .customerCode(order.getCustomerCode())
                .customerName(order.getCustomerName())
                .totalAmount(order.getTotal())
                .orderCode(order.getOrderCode())
                .orderDate(order.getCreatedOn().toLocalDate())
                .lines(lines.stream().map(line -> ProductRentRequest.builder()
                        .startOn(line.getPeriodStart())
                        .finishOn(line.getPeriodFinish())
                        .productCode(line.getProductCode())
                        .build()).collect(Collectors.toList()))
                .build();
        outbox.addMessage(event, "orders", event.getClass().getCanonicalName());
    }

    
}
