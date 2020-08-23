package io.github.bael.mscourse.inventory.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_period")
@Data
public class SchedulePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    private SKU item;

    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private OrderShipment orderShipment;

    @Column(name = "period_start")
    private LocalDate periodStart;

    @Column(name = "period_finish")
    private LocalDate periodFinish;


}
