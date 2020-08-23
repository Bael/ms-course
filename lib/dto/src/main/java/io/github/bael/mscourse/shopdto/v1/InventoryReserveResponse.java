package io.github.bael.mscourse.shopdto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InventoryReserveResponse {
    private String orderCode;
    /**
     * Удалось зарезервировать товар
     */
    private boolean orderReserved;
}
