package io.github.bael.mscourse.orders.rest.dto;

import lombok.Data;

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
