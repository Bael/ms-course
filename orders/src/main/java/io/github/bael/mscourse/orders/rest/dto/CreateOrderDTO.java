package io.github.bael.mscourse.orders.rest.dto;

import io.github.bael.mscourse.orders.entity.Customer;
import io.github.bael.mscourse.orders.entity.OrderStatus;
import io.github.bael.mscourse.orders.rest.OrderLineDTO;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CreateOrderDTO {
    private UUID uid;
    private UUID customerUid;
    private String customerName;
    private String deliveryAddress;
    private List<OrderLineDTO> orderLines;
}
