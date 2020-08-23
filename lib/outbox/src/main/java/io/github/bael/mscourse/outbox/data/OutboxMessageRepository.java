package io.github.bael.mscourse.outbox.data;

import io.github.bael.mscourse.outbox.entity.OutboxMessage;
import io.github.bael.mscourse.outbox.entity.OutboxMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OutboxMessageRepository extends CrudRepository<OutboxMessage, Long>,
        JpaRepository<OutboxMessage, Long> {
    List<OutboxMessage> findAllByMessageStatusOrderByCreatedOn(OutboxMessageStatus status);

    Optional<OutboxMessage> findByMessageId(String messageId);
}
