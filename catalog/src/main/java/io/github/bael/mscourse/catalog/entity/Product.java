package io.github.bael.mscourse.catalog.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 300)
    private String name;

    @Column(length = 300)
    private String code;

    @Column(length = 2000)
    private String description;

    @Column
    private BigDecimal price;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

//    @Column
//    private Double rating;
//
//    @Column(name = "rating_count")
//    private int ratingCount;
//
//    @Column(name = "rating_sum")
//    private long ratingSum;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

//    public Double calculateRating() {
//        if (ratingCount == 0)
//            return null;
//        return (double) (ratingSum / ratingCount);
//    }
}
