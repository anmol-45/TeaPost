package com.company.teaPost.controllers;

import com.company.teaPost.dto.ProductCreateRequest;
import com.company.teaPost.dto.ProductResponse;
import com.company.teaPost.dto.UpdateProductRequest;
import com.company.teaPost.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/products")
    public ProductResponse createProduct(@RequestBody ProductCreateRequest request) {

        log.info("API request received to create product");

        return productService.createProduct(request);
    }

    @GetMapping("/products")
    public Page<ProductResponse> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("API request received to fetch products");

        return productService.getProducts(categoryId, page, size);
    }
    @GetMapping("/products/{productId}")
    public ProductResponse getProductById(@PathVariable Long productId) {

        log.info("API request received to fetch product productId={}", productId);

        return productService.getProductById(productId);
    }

    @PutMapping("/admin/products/{productId}")
    public ProductResponse updateProduct(
            @PathVariable Long productId,
            @RequestBody UpdateProductRequest request) {

        log.info("API request received to update product productId={}", productId);

        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/admin/products/{productId}")
    public String deleteProduct(@PathVariable Long productId) {

        log.info("API request received to delete product productId={}", productId);

        return productService.deleteProduct(productId);
    }

}