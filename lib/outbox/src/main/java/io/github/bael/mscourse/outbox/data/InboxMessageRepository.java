package io.github.bael.mscourse.outbox.data;

import io.github.bael.mscourse.outbox.entity.InboxMessage;
import io.github.bael.mscourse.outbox.entity.InboxMessageStatus;
import io.github.bael.mscourse.outbox.entity.OutboxMessage;
import io.github.bael.mscourse.outbox.entity.OutboxMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InboxMessageRepository extends CrudRepository<InboxMessage, Long>,
        JpaRepository<InboxMessage, Long> {

    List<InboxMessage> findAllByMessageStatusOrderByCreatedOn(InboxMessageStatus status);

    Optional<InboxMessage> findByMessageId(String messageId);
}
