package io.github.bael.mscourse.outbox.service;

import io.github.bael.mscourse.outbox.data.OutboxMessageRepository;
import io.github.bael.mscourse.outbox.entity.OutboxMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OutboxServiceTest {

    @Autowired
    private OutboxService outboxService;

    @Autowired
    private OutboxMessageRepository repository;


    public class ExampleMessage {
        private String code;
        private String name;

        public ExampleMessage(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public ExampleMessage() {
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    void testAddMessage() {
        ExampleMessage message = new ExampleMessage();
        message.setCode("ZERO CODE");
        message.setName("ZERO name");

        int sizeBefore = repository.findAll().size();
        String id = outboxService.addMessage(message, "test", ExampleMessage.class.getCanonicalName());

        Optional<OutboxMessage> byMessageId = repository.findByMessageId(id);
        assertTrue(byMessageId.isPresent());
        OutboxMessage outBoxMessage = byMessageId.orElseThrow();
        assertEquals(ExampleMessage.class.getCanonicalName(), outBoxMessage.getMessageType());
        assertEquals("test", outBoxMessage.getMessageTopic());

        assertEquals(sizeBefore + 1, repository.findAll().size());

    }
}