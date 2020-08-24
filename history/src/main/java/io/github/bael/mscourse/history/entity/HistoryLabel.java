package io.github.bael.mscourse.history.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "history_label")
public class HistoryLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "history_event_id")
    private HistoryEvent event;

}
