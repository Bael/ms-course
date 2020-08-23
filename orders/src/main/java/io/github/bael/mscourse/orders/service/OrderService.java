package io.github.bael.mscourse.orders.service;

import io.github.bael.mscourse.orders.data.OrderLineRepository;
import io.github.bael.mscourse.orders.data.OrderRepository;
import io.github.bael.mscourse.orders.entity.Order;
import io.github.bael.mscourse.orders.entity.OrderLine;
import io.github.bael.mscourse.orders.entity.OrderStatus;

import io.github.bael.mscourse.orders.rest.dto.OrderLineDTO;
import io.github.bael.mscourse.orders.rest.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setCustomerCode(orderRequest.getCustomerCode());
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setOrderStatus(OrderStatus.CREATED);
//        serviceConnector.reserveProducts()
        orderRepository.save(order);
        // save lines
        BigDecimal orderTotalSum  = orderRequest.getLinesList().stream()
                .map(lineDTO -> createOrderLine(order, lineDTO))
                .map(OrderLine::getAmount)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        // dummy number series
        order.setOrderNumber(order.getId().toString());
        order.setTotal(orderTotalSum);
        orderRepository.save(order);
        return order;
    }

    private OrderLine createOrderLine(Order order, OrderLineDTO lineDTO) {
        OrderLine line = new OrderLine();
        line.setPeriodStart(lineDTO.getPeriodStart());
        line.setPeriodFinish(lineDTO.getPeriodFinish());
        line.setProductCode(lineDTO.getProductCode());
        line.setAmount(lineDTO.getAmount());
        line.setOrder(order);
        line.setCreatedOn(LocalDateTime.now(ZoneOffset.UTC));
        line.setQuantity(1d);
        line.setProductName(lineDTO.getProductName());
        orderLineRepository.save(line);
        return line;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    private final OrderLineRepository orderLineRepository;
    public List<OrderLine> getLines(Order order) {
        return orderLineRepository.findAllByOrder(order);
    }
}
