package io.github.bael.mscourse.catalog.data;

import io.github.bael.mscourse.catalog.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long>, JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByName(String name);
    
}
