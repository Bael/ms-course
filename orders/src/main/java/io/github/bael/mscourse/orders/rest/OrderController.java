package io.github.bael.mscourse.orders.rest;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.rest.dto.CreateOrderDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderLineDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderRequest;
import io.github.bael.mscourse.orders.service.OrderService;

import io.github.bael.mscourse.orders.service.OrderStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderStateService orderStateService;

    @PostMapping("/")
    public OrderDTO createOrder(@RequestHeader("X-Email") String email,
                                @RequestHeader("X-First-Name") String firstName,
                                @RequestHeader("X-Last-Name") String lastName,
                                @RequestBody OrderRequest request) {

        request.setCustomerCode(email);
        request.setCustomerName(firstName + " " + lastName);
        return orderStateService.createOrder(request);
    }

    @GetMapping("/")
    public OrderRequest test(OrderRequest request) {

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCustomerCode("100");
        orderRequest.setCustomerName("Ivan ");
        orderRequest.setDeliveryAddress("Krasnoyarsk, Voronova 2222");
        List<OrderLineDTO> lines = new ArrayList<>();
        orderRequest.setLinesList(lines);
        lines.add(OrderLineDTO.builder()
                .amount(BigDecimal.TEN)
                .productCode("T001")
                .productName("XIiaomi WalkingDead A1 Pro Black")
                .periodStart(LocalDate.of(2020, 1, 1))
                .periodFinish(LocalDate.of(2020, 1, 31))
                .build());

        return orderRequest;
    }


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
