package io.github.bael.mscourse.inventory.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SKU {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "product_number")
    private String productNumber;

    @Enumerated(EnumType.STRING)
    private SKUStatus status;

}
