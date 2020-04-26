package io.github.bael.mscourse.shop.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OrdersController {

    @GetMapping("/health")
    public Map<String, String> test() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "OK");
        return map;
    }
}
