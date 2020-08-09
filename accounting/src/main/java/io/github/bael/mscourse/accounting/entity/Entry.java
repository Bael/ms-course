package io.github.bael.mscourse.accounting.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Record about financial operation
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column
    private BigDecimal amount;

    @Column(name = "entry_type" )
    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @Column
    private UUID accountId;

    @Column
    private UUID orderId;


}
