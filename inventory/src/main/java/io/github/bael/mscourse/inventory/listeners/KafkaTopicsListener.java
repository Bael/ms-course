package io.github.bael.mscourse.inventory.listeners;

import io.github.bael.mscourse.outbox.service.InboxProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTopicsListener {
    private final InboxProcessor inboxProcessor;

    @SneakyThrows
    @KafkaListener(topics = "accounting")
    public void listen(String message) {
        log.info("Поступило сообщение {} из топика счетов. ", message);
        inboxProcessor.process(message);
    }
}
    

