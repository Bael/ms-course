package io.github.bael.mscourse.outbox.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bael.mscourse.outbox.data.OutboxMessageRepository;
import io.github.bael.mscourse.outbox.entity.OutboxMessage;
import io.github.bael.mscourse.outbox.entity.OutboxMessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OutboxService implements Outbox {

    private final ObjectMapper objectMapper;

    public OutboxService(OutboxMessageRepository outboxMessageRepository, OutBoxSender outBoxSender) {
        this.outboxMessageRepository = outboxMessageRepository;
        this.outBoxSender = outBoxSender;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new SimpleModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private final OutboxMessageRepository outboxMessageRepository;

    @SneakyThrows
    @Override
    @Transactional
    public <T> String addMessage(T payload, String topic, String messageType) {
        log.info("Add message called: topic {} messageType {} payload {}", topic, messageType, payload);
        OutboxMessage message = new OutboxMessage();
        message.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        message.setMessageStatus(OutboxMessageStatus.CREATED);
        message.setMessageTopic(topic);
        message.setMessageType(messageType);
        // unique id
        String messageId = UUID.randomUUID().toString();
        message.setMessageId(messageId);

        String payloadString = objectMapper.writeValueAsString(payload);
        message.setPayload(payloadString);
        outboxMessageRepository.save(message);

        log.info("Message successfully saved to database");
        return messageId;
    }

    private final OutBoxSender outBoxSender;

    @Scheduled(fixedDelay = 10000L)
    public void poll() {
        List<OutboxMessage> messagesToSent = outboxMessageRepository.findAllByMessageStatusOrderByCreatedOn(OutboxMessageStatus.CREATED);
        if (!messagesToSent.isEmpty()) {
            log.debug("Polling messages. Have messages for processing.");
            messagesToSent.forEach(outBoxSender::sendMessage);
        } else {
            log.debug("No messages to send founded!");

        }
    }
}
