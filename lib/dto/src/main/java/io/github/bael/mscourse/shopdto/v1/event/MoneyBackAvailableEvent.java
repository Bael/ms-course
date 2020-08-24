package io.github.bael.mscourse.shopdto.v1.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyBackAvailableEvent {
    private String orderCode;
    private LocalDate orderDate;
    private LocalDateTime deliveredOn;
    private String customerCode;
}
