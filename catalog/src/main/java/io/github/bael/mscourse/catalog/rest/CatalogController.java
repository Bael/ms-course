package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.ProductCategory;
import io.github.bael.mscourse.catalog.service.BrandService;
import io.github.bael.mscourse.catalog.service.ProductCategoryService;
import io.github.bael.mscourse.catalog.service.ProductSearchFilter;
import io.github.bael.mscourse.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    private final ProductService productService;
    private final BrandService brandService;
    private final ProductCategoryService categoryService;

    @GetMapping("/")
    public List<ProductDTO> all() {
        return productService.findAll();
    }

    @PostMapping("/search")
    public List<ProductDTO> search(@RequestBody ProductSearchFilter searchFilter) {
        String catalog_app_use_cache = System.getenv("CATALOG_APP_USE_CACHE");

        boolean useCache = Boolean.parseBoolean(catalog_app_use_cache);

        log.info("Use cache is  {}", useCache);

        return productService.findProductsByFilter(searchFilter, useCache);
    }

    @GetMapping("/brand")
    public List<BrandDTO> allBrands() {
        return  brandService.findAll().stream().map(BrandDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/test")
    public List<LocalDate> allDates() {
        return Collections.singletonList(LocalDate.now());
    }

    @GetMapping("/category")
    public List<ProductCategoryDTO> allCategories() {
        return categoryService.findAll().stream().map(ProductCategoryDTO::of).collect(Collectors.toList());
    }


}
