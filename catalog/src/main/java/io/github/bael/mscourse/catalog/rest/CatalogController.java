package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.entity.ProductCategory;
import io.github.bael.mscourse.catalog.service.BrandService;
import io.github.bael.mscourse.catalog.service.ProductCategoryService;
import io.github.bael.mscourse.catalog.service.ProductSearchFilter;
import io.github.bael.mscourse.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
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
        boolean useCache = true;
        if (catalog_app_use_cache != null) {
            useCache = Boolean.getBoolean(catalog_app_use_cache);
        }
        return productService.findProductsByFilter(searchFilter, useCache);
    }

    @GetMapping("/brand")
    public List<BrandDTO> allBrands() {
        return  brandService.findAll().stream().map(BrandDTO::of).collect(Collectors.toList());
    }

    @GetMapping("/category")
    public List<ProductCategoryDTO> allCategories() {
        return categoryService.findAll().stream().map(ProductCategoryDTO::of).collect(Collectors.toList());
    }


}
