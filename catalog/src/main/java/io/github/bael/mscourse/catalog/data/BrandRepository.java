package io.github.bael.mscourse.catalog.data;

import io.github.bael.mscourse.catalog.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, Long>, JpaRepository<Brand, Long> {
    

}
