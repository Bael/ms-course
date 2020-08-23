package io.github.bael.mscourse.catalog.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "product_category")
@Data
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 300)
    private String name;

}
