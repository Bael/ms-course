package io.github.bael.mscourse.catalog.service;

import io.github.bael.mscourse.catalog.data.BrandRepository;
import io.github.bael.mscourse.catalog.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Cacheable(value = "brands", condition = "#useCache")
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
