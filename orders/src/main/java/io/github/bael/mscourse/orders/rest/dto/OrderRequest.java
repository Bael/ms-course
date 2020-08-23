package io.github.bael.mscourse.orders.rest.dto;

import io.github.bael.mscourse.shopdto.v1.ProductRentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {
    private String customerCode;
    private String customerName;
    private String deliveryAddress;
    private List<OrderLineDTO> linesList;
}
