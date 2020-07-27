package io.github.bael.mscourse.orders.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderLineDTO {
    private UUID uid;
    private BigDecimal amount;
    private double quantity;
    private String productName;
}
