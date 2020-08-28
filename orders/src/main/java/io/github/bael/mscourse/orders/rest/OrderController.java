package io.github.bael.mscourse.orders.rest;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.rest.dto.CreateOrderDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderLineDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderRequest;
import io.github.bael.mscourse.orders.service.OrderService;

import io.github.bael.mscourse.orders.service.OrderStateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderStateService orderStateService;

    @PostMapping("/")
    public OrderDTO createOrder(@RequestHeader("X-Email") String email,
                                @RequestHeader("X-First-Name") String firstName,
                                @RequestHeader("X-Last-Name") String lastName,
                                @RequestBody OrderRequest request) {


        log.info("Создание заказа:{}, пользователем {} {} {} ", request, email, firstName, lastName);
        request.setCustomerCode(email);
        request.setCustomerName(firstName + " " + lastName);
        OrderDTO order = orderStateService.createOrder(request);
        log.info("Заказа создан: {}", order);
        return order;
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
