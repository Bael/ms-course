package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.Product;
import io.github.bael.mscourse.catalog.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private int availableQuantity;
    private Double rating;
    private ProductStatus status;

    public static ProductDTO of(Product product) {
        return builder().availableQuantity(product.getAvailableQuantity())
                .code(product.getCode())
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
//                .rating(product.getRating())
                .status(product.getStatus()).build();
    }
}
