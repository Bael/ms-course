package io.github.bael.mscourse.catalog.service;

import io.github.bael.mscourse.catalog.data.ProductRepository;
import io.github.bael.mscourse.catalog.entity.Product;
import io.github.bael.mscourse.catalog.rest.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
//    private final ReviewRepository reviewRepository;

    private void fillEntity(Product product, ProductDTO dto) {
        product.setCode(dto.getCode());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStatus(dto.getStatus());
    }

    private void fillDto(Product product, ProductDTO dto) {
        dto.setCode(product.getCode());
        dto.setDescription(product.getDescription());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStatus(product.getStatus());
//        dto.setRating(product.calculateRating());
//
//        List<ReviewDTO> lastReviews = reviewRepository.findTop10ByProduct_Id(dto.getId())
//                .stream().map(ReviewDTO::of).collect(Collectors.toList());
//        dto.setLastReviews(lastReviews);
    }

    public ProductDTO create(ProductDTO createDTO) {
        Product product = new Product();
        fillEntity(product, createDTO);
//        product.setRatingCount(0);
//        product.setRatingSum(0);
        productRepository.save(product);
        createDTO.setId(product.getId());
        return createDTO;
    }

    public ProductDTO update(ProductDTO dto) {
        Product product = getProduct(dto.getId());
        fillEntity(product, dto);
        return dto;
    }

//    public ProductDTO addReview(ReviewDTO dto) {
//        Product product = productRepository.findByCode(dto.getProductCode())
//                .orElseThrow(() -> new ObjectNotFoundException(dto.getProductCode(), "Review"));
////
////        Review review = new Review();
//////        review.setDescription(dto.getDescription());
//////        review.setProduct(product);
//////        review.setReviewer(dto.getReviewer());
//////        review.setRating(dto.getRating());
////        reviewRepository.save(review);
////
////        product.setRatingCount(product.getRatingCount() + 1);
////        product.setRatingSum(product.getRatingSum() + dto.getRating().ordinal());
////        productRepository.save(product);
//        return get(product.getId());
//    }

    public ProductDTO get(Long id) {
        Product product = getProduct(id);
        ProductDTO dto = new ProductDTO();
        fillDto(product, dto);
        return dto;
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Product"));
    }


    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(ProductDTO::of).collect(Collectors.toList());
    }

    @Cacheable(value = "products", condition = "#useCache", key = "#filter")
    public List<ProductDTO> findProductsByFilter(ProductSearchFilter filter, boolean useCache) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("findProductsByFilter is called!");
        Specification<Product> productSpecification = alwaysTrue();
        if (filter.getBrandId() != null) {
            productSpecification = productSpecification.and(findByBrand(filter.getBrandId()));
        }

        if (filter.getCategoryId() != null) {
            productSpecification = productSpecification.and(findByCategoryId(filter.getCategoryId()));
        }
        if (filter.getName() != null) {
            productSpecification = productSpecification.and(findByName(filter.getName()));
        }

        return productRepository.findAll(productSpecification).stream().map(ProductDTO::of).collect(Collectors.toList());
    }

    public Specification<Product> findByBrand(Long brandId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("id"), brandId);
    }

    public Specification<Product> alwaysTrue() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get("id"));
    }

    public Specification<Product> findByCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public Specification<Product> findByName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }



    public List<ProductDTO> search(String request) {
        return productRepository.searchAll("%" + request + "%").stream()
                .map(ProductDTO::of).collect(Collectors.toList());
    }
}
