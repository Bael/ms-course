package io.github.bael.mscourse.shopdto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InventoryReserveRequest {
    private String orderCode;
    private String customerCode;
    private List<ProductRentRequest> requestDTOList;
}
