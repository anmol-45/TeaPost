package com.company.teaPost.services;

import com.company.teaPost.requestDto.ProductCreateRequest;
import com.company.teaPost.responseDto.ProductResponse;
import com.company.teaPost.requestDto.UpdateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    Page<ProductResponse> getProducts(Long categoryId, int page, int size);
    ProductResponse getProductById(Long productId);

    ProductResponse updateProduct(Long productId, UpdateProductRequest request);
    String deleteProduct(Long productId);

}