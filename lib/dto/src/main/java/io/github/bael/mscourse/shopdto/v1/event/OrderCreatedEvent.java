package io.github.bael.mscourse.shopdto.v1.event;

import io.github.bael.mscourse.shopdto.v1.ProductRentRequest;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String orderCode;
    private String customerCode;
    private String customerName;
    private BigDecimal totalAmount;
    private List<ProductRentRequest> lines;

}
