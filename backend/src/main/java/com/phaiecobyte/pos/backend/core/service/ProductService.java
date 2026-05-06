package com.phaiecobyte.pos.backend.core.service;

import com.phaiecobyte.pos.backend.core.dto.ProductReq;
import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductRes createProduct(ProductReq request);
    ProductRes updateProduct(UUID id, ProductReq request);
    ProductRes getProductById(UUID id);
    Page<ProductRes> getAllProducts(Pageable pageable);
    List<ProductRes> getProductsByCategory(UUID categoryId);
    void deleteProduct(UUID id);
}