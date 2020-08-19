package io.github.bael.mscourse.catalog.entity;

import javax.persistence.*;

@Entity
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 300)
    private String name;

}
