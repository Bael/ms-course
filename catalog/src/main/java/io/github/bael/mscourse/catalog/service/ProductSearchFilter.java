package io.github.bael.mscourse.catalog.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchFilter implements Serializable {
    private Long brandId;
    private Long categoryId;
    private String name;
}
