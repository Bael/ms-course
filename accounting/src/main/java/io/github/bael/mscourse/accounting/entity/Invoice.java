package io.github.bael.mscourse.accounting.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "invoice")
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "customer_code")
    private String customerCode;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column
    private BigDecimal amount;

    @Column(name = "invoice_status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

}
