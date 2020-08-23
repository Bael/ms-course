package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.ProductCategory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryDTO {
    private long id;
    private String name;
    public static ProductCategoryDTO of(ProductCategory productCategory) {
        return ProductCategoryDTO.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .build();
    }
}
