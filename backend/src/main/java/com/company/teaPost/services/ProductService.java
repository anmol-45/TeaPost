package com.company.teaPost.services;

import com.company.teaPost.requestDto.ProductCreateRequest;
import com.company.teaPost.responseDto.ProductResponse;
import com.company.teaPost.requestDto.UpdateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request);

    Page<ProductResponse> getProducts(String categoryId, int page, int size);
    ProductResponse getProductById(String productId);

    ProductResponse updateProduct(String productId, UpdateProductRequest request);
    String deleteProduct(String productId);

}