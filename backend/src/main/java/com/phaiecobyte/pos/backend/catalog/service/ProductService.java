package com.phaiecobyte.pos.backend.catalog.service;

import com.phaiecobyte.pos.backend.catalog.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    ProductDto.Res createProduct(ProductDto.Req request);
    ProductDto.Res updateProduct(UUID id, ProductDto.Req request);
    ProductDto.Res getProductById(UUID id);
    Page<ProductDto.Res> getAllProducts(Pageable pageable);
    Page<ProductDto.Res> getProductsByCategory(UUID categoryId,Pageable pageable);
    void deleteProduct(UUID id);
}