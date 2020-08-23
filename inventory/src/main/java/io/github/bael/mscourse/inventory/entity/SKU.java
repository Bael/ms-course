package io.github.bael.mscourse.inventory.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sku")
public class SKU {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "product_code")
    private String productCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "sku_status")
    private SKUStatus status;

}
