package io.github.bael.mscourse.orders.rest.dto;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.entity.OrderLine;
import io.github.bael.mscourse.orders.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                .orderNumber(order.getOrderCode())
                .id(order.getId())
                .deliveryAddress(order.getDeliveryAddress())
                .lines(lines.stream().map(OrderLineDTO::of).collect(Collectors.toList()))
                .orderStatus(order.getOrderStatus())
                .orderStatusDescription(order.getOrderStatusDescription())
                .total(order.getTotal()).build();

    }
}
