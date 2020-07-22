package io.github.bael.mscourse.catalog.data;

import io.github.bael.mscourse.catalog.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByCode(String code);

}
