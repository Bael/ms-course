package io.github.bael.mscourse.inventory.rest;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SKUShortInfoDTO {
    private String SKU;
    private String description;

}
