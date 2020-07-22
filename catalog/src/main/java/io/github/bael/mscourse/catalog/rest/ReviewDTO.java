package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.Product;
import io.github.bael.mscourse.catalog.entity.RatingEnum;
import io.github.bael.mscourse.catalog.entity.Review;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
@Data
@Builder
public class ReviewDTO {
    private Long id;
    private String productCode;
    private String reviewer;
    private String description;
    private RatingEnum rating;

    public static ReviewDTO of(Review review) {
        return ReviewDTO.builder()
                .description(review.getDescription())
                .productCode(review.getProduct().getCode())
                .reviewer(review.getReviewer())
                .rating(review.getRating())
                .build();
    }
}
