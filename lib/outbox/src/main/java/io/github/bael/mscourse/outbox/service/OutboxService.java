package io.github.bael.mscourse.outbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequiredArgsConstructor
@Slf4j
public class OutboxService implements Outbox {

    private final ObjectMapper objectMapper;
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
        }
    }
}
