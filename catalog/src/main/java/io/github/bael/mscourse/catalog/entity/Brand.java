package io.github.bael.mscourse.catalog.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 300)
    private String name;

}
