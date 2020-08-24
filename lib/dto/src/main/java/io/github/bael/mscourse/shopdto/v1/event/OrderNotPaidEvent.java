package io.github.bael.mscourse.shopdto.v1.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotPaidEvent {
    private String orderCode;
    private LocalDate orderDate;
    private LocalDateTime overdueOn;
    private String customerCode;
    private BigDecimal totalAmount;
}
