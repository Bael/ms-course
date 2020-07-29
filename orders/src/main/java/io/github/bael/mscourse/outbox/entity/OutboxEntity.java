package io.github.bael.mscourse.outbox.entity;

import org.apache.logging.log4j.message.Message;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class OutboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private UUID uuid;

    @Column
    private String topic;

    @Column
    private String type;

    @Column(length = 50000)
    private String payload;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "sent_on")
    private LocalDateTime sentOn;

    @Enumerated(EnumType.STRING)
    private OutboxMessageStatus outboxMessageStatus;

}
