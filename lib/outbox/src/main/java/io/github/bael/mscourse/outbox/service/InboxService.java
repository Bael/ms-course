package io.github.bael.mscourse.outbox.service;

import io.github.bael.mscourse.outbox.data.InboxMessageRepository;
import io.github.bael.mscourse.outbox.entity.InboxMessage;
import io.github.bael.mscourse.outbox.entity.InboxMessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class InboxService {
    private final InboxMessageRepository inboxMessageRepository;
    public boolean registerInbox(String messageId) {
        if (inboxMessageRepository.findByMessageId(messageId).isEmpty()) {
            InboxMessage message = new InboxMessage();
            message.setMessageId(messageId);
            message.setMessageStatus(InboxMessageStatus.CREATED);
            message.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
            inboxMessageRepository.save(message);
            return true;
        } else {
            return false;
        }
    }
}
