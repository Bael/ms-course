package io.github.bael.mscourse.catalog.service;

import io.github.bael.mscourse.catalog.data.ProductCategoryRepository;
import io.github.bael.mscourse.catalog.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }
}
