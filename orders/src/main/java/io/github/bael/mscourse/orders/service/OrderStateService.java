package io.github.bael.mscourse.orders.service;

import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.entity.OrderLine;
import io.github.bael.mscourse.orders.entity.OrderStatus;
import io.github.bael.mscourse.orders.external.InventoryRestServiceConnector;
import io.github.bael.mscourse.orders.rest.dto.OrderDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderRequest;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveRequest;
import io.github.bael.mscourse.shopdto.v1.ProductRentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStateService {

    private final OrderService orderService;
    private final InventoryRestServiceConnector inventoryConnector;
    


    public OrderDTO createOrder(OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        if (reserveInventory(orderRequest, order.getOrderNumber())) {
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setOrderStatusDescription("Order is reserved and waiting payment");
        } else {
            order.setOrderStatus(OrderStatus.CANCELLED);
            order.setOrderStatusDescription("Item(s) are not available at required date period");
        }
        orderService.save(order);
        List<OrderLine> lines = orderService.getLines(order);
        return OrderDTO.of(order, lines);
    }

    private boolean reserveInventory(OrderRequest orderRequest, String orderNumber) {
        List<ProductRentRequest> items = orderRequest.getLinesList()
                .stream().map(line -> ProductRentRequest.builder()
                        .productCode(line.getProductCode())
                        .finishOn(line.getPeriodFinish())
                        .startOn(line.getPeriodStart())
                        .build()).collect(Collectors.toList());

        InventoryReserveRequest request = InventoryReserveRequest.builder()
                .customerCode(orderRequest.getCustomerCode())
                .orderCode(orderNumber)
                .requestDTOList(items)
                .build();
        return inventoryConnector.reserveProducts(request).isOrderReserved();
    }

}
