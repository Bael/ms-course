package io.github.bael.mscourse.outbox.service;

import io.github.bael.mscourse.outbox.entity.OutboxMessage;

public interface OutBoxSender {
    void sendMessage(OutboxMessage message);
}
