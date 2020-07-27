package io.github.bael.mscourse.orders.rest;

import io.github.bael.mscourse.orders.entity.Customer;
import io.github.bael.mscourse.orders.entity.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateOrderDTO {
    private UUID uid;
    private UUID customerUid;
    private String customerName;
    private String deliveryAddress;
}
