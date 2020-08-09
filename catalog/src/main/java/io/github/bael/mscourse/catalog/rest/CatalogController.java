package io.github.bael.mscourse.catalog.rest;

import io.github.bael.mscourse.catalog.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final ProductService productService;
    @GetMapping("/")
    public List<ProductDTO> all() {
        return productService.findAll();
    }

    @GetMapping("/search?")
    public List<ProductDTO> search(@RequestParam String search) {
        return Collections.emptyList();
    }


}
