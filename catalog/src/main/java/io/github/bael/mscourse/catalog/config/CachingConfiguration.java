package io.github.bael.mscourse.catalog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class CachingConfiguration {

    @Value("${enable.hazelcast.kubernetes}")
    private boolean isKubernetesConfig;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(60, TimeUnit.SECONDS)
                                .maximumSize(1000)
                                .build().asMap(),
                        false);
            }
        };
    }
//
//
//    @Bean
//    CacheManager cacheManager() {
//        log.info("Настройка hazelcast. isKubernetesConfig =  {}", isKubernetesConfig);
////        if (!isKubernetesConfig) {
////            log.info("Настройка hazelcast как встроенного кэша");
////            return new HazelcastCacheManager(Hazelcast.newHazelcastInstance());
////        }
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.getNetworkConfig().getKubernetesConfig().setEnabled(false);
//        return new HazelcastCacheManager(Hazelcast.newHazelcastInstance());
//
////        log.info("Настройка hazelcast как клиента сервера");
////
////
////        return new HazelcastCacheManager(HazelcastClient.newHazelcastClient(clientConfig));
//    }
}
