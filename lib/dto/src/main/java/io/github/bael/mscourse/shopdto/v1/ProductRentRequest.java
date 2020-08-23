package io.github.bael.mscourse.shopdto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * запрос аренды
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRentRequest {
    private String productCode;
    private LocalDate startOn;
    private LocalDate finishOn;
}
