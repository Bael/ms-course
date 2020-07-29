package io.github.bael.mscourse.orders.rest;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderLineDTO {
    private UUID uid;
    private BigDecimal amount;
    private double quantity;
    private String SKU;
    private LocalDateTime periodStart;
    private LocalDateTime periodFinish;
}
