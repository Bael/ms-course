package io.github.bael.mscourse.history.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "history_event")
public class HistoryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "change_by")
    private String changeBy;

    @Column(name = "payload", length = 2000)
    private String payload;

    @Column(name = "name", length = 2000)
    private String name;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

}
