package io.github.bael.mscourse.outbox.service;

public interface Outbox {
    <T> String addMessage(T payload, String topic, String messageType);
}
