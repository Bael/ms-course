package io.github.bael.mscourse.accounting.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRegistrationDTO {
    private String customerCode;
    private String orderCode;
    private String paymentId;
    private LocalDate paidOn;
    private BigDecimal paidSum;
}
