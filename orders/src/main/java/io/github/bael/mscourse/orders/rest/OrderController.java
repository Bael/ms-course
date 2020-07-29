package io.github.bael.mscourse.orders.rest;

import io.github.bael.mscourse.orders.rest.dto.CreateOrderDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    @PostMapping("/orders")
    public void createOrder(CreateOrderDTO dto) {

    }

    @PutMapping("/orders/{uid}/cancel")
    public void cancelOrder(UUID uid) {

    }

//    @PutMapping("/orders/{uid}/confirm")
//    public void confirmOrder(UUID uid) {
//
//    }
//
//    @PutMapping("/orders/{uid}/paid-type")
//    public void choosePaidType(UUID uid) {
//
//    }

    @GetMapping("/orders/{uid}/info")
    public OrderDTO info(UUID uid) {
        return null;
    }

//    @GetMapping("/orders/{uid}/history")
//    public void history(UUID uid) {
//    }


}
