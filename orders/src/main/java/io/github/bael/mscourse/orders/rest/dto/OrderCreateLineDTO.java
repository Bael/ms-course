package io.github.bael.mscourse.orders.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderCreateLineDTO {
    private BigDecimal amount;
    private double quantity;
    private String SKU;
    private LocalDateTime periodStart;
    private LocalDateTime periodFinish;
}
