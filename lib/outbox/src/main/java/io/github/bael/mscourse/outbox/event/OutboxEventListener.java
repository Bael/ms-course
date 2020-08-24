package io.github.bael.mscourse.outbox.event;

public interface OutboxEventListener<T> {
    boolean supports(String eventType);
    void handle(T event);
}
