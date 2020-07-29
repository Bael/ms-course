package io.github.bael.mscourse.inventory.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule_period")
@Data
public class SchedulePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SKU_id")
    private SKU item;

    @Column(name = "period_start")
    private LocalDateTime periodStart;

    @Column(name = "period_finish")
    private LocalDateTime periodFinish;


}
