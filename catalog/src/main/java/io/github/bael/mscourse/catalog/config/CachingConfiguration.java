package io.github.bael.mscourse.catalog.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachingConfiguration {

    @Value("${enable_hazelcast_kubernetes}")
    private boolean isKubernetesConfig;
    @Bean
    CacheManager cacheManager() {
        if (!isKubernetesConfig) {
            return new HazelcastCacheManager(Hazelcast.newHazelcastInstance());
        }
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().getKubernetesConfig().setEnabled(true);
        return new HazelcastCacheManager(HazelcastClient.newHazelcastClient(clientConfig));
    }
}
