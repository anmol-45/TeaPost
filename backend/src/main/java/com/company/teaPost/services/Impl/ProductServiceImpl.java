package com.company.teaPost.services.Impl;

import com.company.teaPost.requestDto.ProductCreateRequest;
import com.company.teaPost.responseDto.ProductResponse;
import com.company.teaPost.requestDto.UpdateProductRequest;
import com.company.teaPost.entities.Product;
import com.company.teaPost.repositories.ProductRepository;
import com.company.teaPost.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {

        log.info("Product creation started name={} categoryId={}",
                request.getName(), request.getCategoryId());

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .categoryId(request.getCategoryId())
                .stock(request.getStock())
                .rating(0.0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        log.info("Product created successfully productId={}", savedProduct.getProductId());

        return mapToResponse(savedProduct);
    }

    @Override
    public Page<ProductResponse> getProducts(String categoryId, int page, int size) {

        log.info("Fetching products categoryId={} page={} size={}",
                categoryId, page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());

        Page<Product> products;

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }

        log.info("Fetched {} products", products.getTotalElements());

        return products.map(this::mapToResponse);
    }

    private ProductResponse mapToResponse(Product product) {

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .rating(product.getRating())
                .stock(product.getStock())
                .build();
    }
    @Override
    public ProductResponse getProductById(String productId) {

        log.info("Fetching product details productId={}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        log.info("Product fetched successfully productId={}", productId);

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(String productId, UpdateProductRequest request) {

        log.info("Updating product productId={}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getCategoryId() != null) {
            product.setCategoryId(request.getCategoryId());
        }

        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }

        product.setUpdatedAt(java.time.LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        log.info("Product updated successfully productId={}", productId);

        return mapToResponse(updatedProduct);
    }

    @Override
    public String deleteProduct(String productId) {

        log.info("Deleting product productId={}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);

        log.info("Product deleted successfully productId={}", productId);

        return "Product deleted successfully";
    }


}