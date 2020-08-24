package io.github.bael.mscourse.history.config;

import io.github.bael.mscourse.outbox.config.KafkaSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groupId}")
    private String groupId;

    @Bean
    public KafkaSettings kafkaSettings() {
        KafkaSettings settings = new KafkaSettings();
        settings.setBootstrapAddress(bootstrapAddress);
        settings.setGroupId(groupId);
        return settings;
    }
}
