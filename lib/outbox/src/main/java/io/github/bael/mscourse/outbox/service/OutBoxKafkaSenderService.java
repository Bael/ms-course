package io.github.bael.mscourse.outbox.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bael.mscourse.outbox.data.OutboxMessageRepository;
import io.github.bael.mscourse.outbox.entity.OutboxMessage;
import io.github.bael.mscourse.outbox.entity.OutboxMessageStatus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class OutBoxKafkaSenderService implements OutBoxSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final OutboxMessageRepository outboxMessageRepository;

    public OutBoxKafkaSenderService(KafkaTemplate<String, String> kafkaTemplate, OutboxMessageRepository outboxMessageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.outboxMessageRepository = outboxMessageRepository;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new SimpleModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @SneakyThrows
    @Override
    public void sendMessage(OutboxMessage message) {
        log.debug("Send message called: {}", message);
        TransportMessage transportMessage = TransportMessage.builder().messageId(message.getMessageId())
                .payload(message.getPayload()).type(message.getMessageType()).build();
        String body = objectMapper.writeValueAsString(transportMessage);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message.getMessageTopic(), body);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                message.setSentOn(LocalDateTime.now(ZoneOffset.UTC));
                message.setMessageStatus(OutboxMessageStatus.SENT);
                outboxMessageRepository.save(message);
                log.info("Sent message=[ {} ] with offset=[ {} ]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[ {} ] ] due to : {}", message, ex.getMessage());
            }
        });

    }
}
