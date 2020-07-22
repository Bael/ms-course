package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private int availableQuantity;
    private Double rating;
    private ProductStatus status;
    private int ratingCount;
    private List<ReviewDTO> lastReviews;
}
