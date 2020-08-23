package io.github.bael.mscourse.outbox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bael.mscourse.outbox.data.OutboxMessageRepository;
import io.github.bael.mscourse.outbox.entity.OutboxMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutBoxKafkaSenderService implements OutBoxSender {

//    @Autowired
//    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper;

    private OutboxMessageRepository outboxMessageRepository;

    
    
    @SneakyThrows
    @Override
    public void sendMessage(OutboxMessage message) {
        log.debug("Send message called: {}", message);
        TransportMessage transportMessage = TransportMessage.builder().messageId(message.getMessageId())
                .payload(message.getPayload()).type(message.getMessageType()).build();
        String body = mapper.writeValueAsString(transportMessage);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(message.getMessageTopic(), body);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                message.setSentOn(LocalDateTime.now(ZoneOffset.UTC));
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
