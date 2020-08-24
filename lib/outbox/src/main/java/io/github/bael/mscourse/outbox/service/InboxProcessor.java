package io.github.bael.mscourse.outbox.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bael.mscourse.outbox.data.InboxMessageRepository;
import io.github.bael.mscourse.outbox.entity.InboxMessage;
import io.github.bael.mscourse.outbox.entity.InboxMessageStatus;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;


@Slf4j
public class InboxProcessor {

    private final List<OutboxEventListener<?>> listeners;
    private final ObjectMapper mapper;

    public InboxProcessor(List<OutboxEventListener<?>> listeners, InboxMessageRepository inboxMessageRepository) {
        this.listeners = listeners;
        this.inboxMessageRepository = inboxMessageRepository;
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new SimpleModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    @SneakyThrows
    @Transactional
    public void process(String message) {
        log.info("Поступило сообщение " + message);
        TransportMessage transportMessage = mapper.readValue(message, TransportMessage.class);
        if (registerInbox(transportMessage.getMessageId(), transportMessage.getType(), transportMessage.getPayload())) {
            log.info("Сообщение ранее не приходило. Обрабатываем.");
            Optional<OutboxEventListener<?>> listenerOpt = findByType(transportMessage.getType());
            if (listenerOpt.isPresent()) {
                handle(transportMessage.getType(), transportMessage.getPayload(), listenerOpt.get());
            } else {
                log.warn("Обработчик не найден. Пропускаем событие.");
            }
        } else {
            log.warn("Сообщение уже было принято ранее! Пропуск обработки.");
        }

    }

    private Optional<OutboxEventListener<?>> findByType(String messageType) {
        return listeners.stream().filter(outboxEventListener -> outboxEventListener.supports(messageType)).findFirst();
    }

    @SneakyThrows
    private <T> void handle(String type, String payload, OutboxEventListener<T> listener) {
        Class<T> aClass = (Class<T>) Class.forName(type);
        T event = mapper.readValue(payload, aClass);
        listener.handle(event);
    }

    private final InboxMessageRepository inboxMessageRepository;
    public boolean registerInbox(String messageId, String type, String payload) {
        if (inboxMessageRepository.findByMessageId(messageId).isEmpty()) {
            InboxMessage message = new InboxMessage();
            message.setMessageId(messageId);
            message.setMessageType(type);
            message.setPayload(payload);

            message.setMessageStatus(InboxMessageStatus.CREATED);
            message.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
            inboxMessageRepository.save(message);
            return true;
        } else {
            return false;
        }
    }


}
