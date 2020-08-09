package io.github.bael.mscourse.shop.rest;

import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrdersController {

    @Autowired
    private MeterRegistry meterRegistry;


    io.micrometer.core.instrument.Counter counter;

    private static final Counter requestTotal = Counter.build()
            .name("http_requests_health")
            .labelNames("method", "handler", "status")
            .help("Http Request Total").register();

    public OrdersController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
         counter = meterRegistry.counter("http_counter", "method", "GET");
    }

    @GetMapping("/health")
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "OK");
        requestTotal.labels("GET", "health", "200").inc();
//        counter.increment(1);
        meterRegistry.counter("http_counter_2", "method", "GET", "handler", "/health", "status", "200").increment();
        return map;
    }
}
