package io.github.bael.mscourse.outbox.config;

import lombok.Data;

@Data
public class KafkaSettings {
    private String groupId;
    private String bootstrapAddress;
}
