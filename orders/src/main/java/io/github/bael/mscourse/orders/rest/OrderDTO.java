package io.github.bael.mscourse.orders.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDTO {
    private UUID uid;
    private UUID customerUid;
    private String customerName;
    private String deliveryAddress;
    private String orderNumber;
    private BigDecimal total;
    private List<OrderLineDTO> lines;

}
