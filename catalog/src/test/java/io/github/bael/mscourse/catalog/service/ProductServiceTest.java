package io.github.bael.mscourse.catalog.service;

import io.github.bael.mscourse.catalog.entity.ProductStatus;
import io.github.bael.mscourse.catalog.entity.RatingEnum;
import io.github.bael.mscourse.catalog.entity.Review;
import io.github.bael.mscourse.catalog.rest.ProductDTO;
import io.github.bael.mscourse.catalog.rest.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testCreate() {
        ProductDTO dto = ProductDTO.builder()
                .availableQuantity(0)
                .code("T-001")
                .description("descr")
                .name("T-800")
                .description("Старый но не бесполезный T-800")
                .status(ProductStatus.ACTIVE)
                .build();
        ProductDTO created = productService.create(dto);
        assertNotNull(created.getId());
        System.out.println(created);
    }

    @Test
    void testCreateReview() {
        ProductDTO dto = ProductDTO.builder()
                .availableQuantity(0)
                .code("T-002")
                .description("descr")
                .name("T-1000")
                .description("Неубиваемый T-1000")
                .status(ProductStatus.ACTIVE)
                .build();
        ProductDTO created = productService.create(dto);
        ReviewDTO review = ReviewDTO.builder().description("отличный робот, любит горячие ванны")
                .productCode("T-002")
                .reviewer("Сара К.")
                .rating(RatingEnum.PERFECT)
                .build();
//        ProductDTO productDTO = productService.addReview(review);
//        assertEquals(5, productDTO.getRating(), 00.1d);

        System.out.println(productService.search("100"));

    }
}