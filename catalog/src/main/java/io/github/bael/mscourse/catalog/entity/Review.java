package io.github.bael.mscourse.catalog.entity;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;

import javax.persistence.*;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private String reviewer;

    @Column
    private String description;

    @Enumerated
    private RatingEnum rating;

}
