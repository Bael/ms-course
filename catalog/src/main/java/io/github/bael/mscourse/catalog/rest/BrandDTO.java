package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.Brand;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandDTO {
    private long id;
    private String name;

    public static BrandDTO of(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
