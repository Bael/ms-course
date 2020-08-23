package io.github.bael.mscourse.orders.rest.dto;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.entity.OrderLine;
import io.github.bael.mscourse.orders.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderDTO {
    private Long id;
    private String customerCode;
    private String customerName;
    private String deliveryAddress;
    private String orderNumber;
    private BigDecimal total;
    private OrderStatus orderStatus;
    private String orderStatusDescription;
    private List<OrderLineDTO> lines;

    public static OrderDTO of(Order order, List<OrderLine> lines) {
        return builder()
                .customerName(order.getCustomerName())
                .customerCode(order.getCustomerCode())
                .orderNumber(order.getOrderNumber())
                .id(order.getId())
                .deliveryAddress(order.getDeliveryAddress())
                .lines(lines.stream().map(OrderLineDTO::of).collect(Collectors.toList()))
                .orderStatus(order.getOrderStatus())
                .orderStatusDescription(order.getOrderStatusDescription())
                .total(order.getTotal()).build();

    }
}
