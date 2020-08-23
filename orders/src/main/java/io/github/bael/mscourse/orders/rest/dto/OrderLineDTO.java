package io.github.bael.mscourse.orders.rest.dto;

import io.github.bael.mscourse.orders.entity.OrderLine;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class OrderLineDTO {
//    private UUID uid;
    private BigDecimal amount;
//    private double quantity;
    private String productCode;
    private String productName;
    private LocalDate periodStart;
    private LocalDate periodFinish;

    public static OrderLineDTO of(OrderLine orderLine) {
        return OrderLineDTO.builder()
                .amount(orderLine.getAmount())
                .periodStart(orderLine.getPeriodStart())
                .periodFinish(orderLine.getPeriodFinish())
                .productCode(orderLine.getProductCode())
                .productName(orderLine.getProductName())
                .build();
    }
}
