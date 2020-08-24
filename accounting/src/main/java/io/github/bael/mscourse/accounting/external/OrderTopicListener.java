package io.github.bael.mscourse.accounting.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bael.mscourse.accounting.service.AccountingAPI;
import io.github.bael.mscourse.outbox.service.InboxProcessor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderTopicListener {
    private final InboxProcessor inboxProcessor;

    @SneakyThrows
    @KafkaListener(topics = "orders")
    public void listen(String message) {
        log.info("Поступило сообщение " + message);
        inboxProcessor.process(message);
    }
}
    

