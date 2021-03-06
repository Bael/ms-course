package io.github.bael.mscourse.orders.config;

import io.github.bael.mscourse.outbox.config.KafkaSettings;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

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
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public NewTopic topicOrders() {
//        return new NewTopic("orders", 1, (short) 1);
//    }
}
