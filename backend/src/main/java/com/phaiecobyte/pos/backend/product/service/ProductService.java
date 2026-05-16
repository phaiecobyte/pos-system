package com.phaiecobyte.pos.backend.product.service;

import com.phaiecobyte.pos.backend.product.dto.ProductReq;
import com.phaiecobyte.pos.backend.product.dto.ProductRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    ProductRes createProduct(ProductReq request);
    ProductRes updateProduct(UUID id, ProductReq request);
    ProductRes getProductById(UUID id);
    Page<ProductRes> getAllProducts(Pageable pageable);
    Page<ProductRes> getProductsByCategory(UUID categoryId,Pageable pageable);
    void deleteProduct(UUID id);
}