package io.github.bael.mscourse.shop.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {

    @GetMapping("/health")
    public String test() {
        return "OK";
    }
}
