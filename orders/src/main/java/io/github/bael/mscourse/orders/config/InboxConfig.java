package io.github.bael.mscourse.orders.config;

import io.github.bael.mscourse.outbox.data.InboxMessageRepository;
import io.github.bael.mscourse.outbox.event.OutboxEventListener;
import io.github.bael.mscourse.outbox.service.InboxProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InboxConfig {
    private final List<OutboxEventListener<?>> listeners;
    private final InboxMessageRepository repository;
    @Bean
    public InboxProcessor inboxProcessor() {
        return new InboxProcessor(listeners, repository);
    }
}
