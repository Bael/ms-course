package io.github.bael.mscourse.outbox.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inbox_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "message_type", length = 500)
    private String messageType;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "payload", length = 20000)
    private String payload;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private InboxMessageStatus messageStatus;

    @Column(name = "message_topic")
    private String messageTopic;
}
