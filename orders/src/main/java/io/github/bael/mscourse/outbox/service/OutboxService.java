package io.github.bael.mscourse.outbox.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class OutboxService {

    @Transactional
    public void add(UUID uid, String topic, Object payload) {

    }

    @Transactional
    @Scheduled(fixedDelay = 2000)
    public void poll() {

    }

}
