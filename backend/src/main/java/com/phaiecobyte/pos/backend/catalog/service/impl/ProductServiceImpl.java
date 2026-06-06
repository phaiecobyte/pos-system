package com.phaiecobyte.pos.backend.product.service.impl;

import com.phaiecobyte.pos.backend.core.logging.LogAudit;
import com.phaiecobyte.pos.backend.product.dto.ProductDto;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import com.phaiecobyte.pos.backend.product.mapper.ProductMapper;
import com.phaiecobyte.pos.backend.product.model.Category;
import com.phaiecobyte.pos.backend.product.model.Product;
import com.phaiecobyte.pos.backend.product.repository.CategoryRepository;
import com.phaiecobyte.pos.backend.product.repository.ProductRepository;
import com.phaiecobyte.pos.backend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    @LogAudit(action = "CREATE", moduleName = "CORE", entityName = "t_core_product", defaultReason = "បង្កើតទំនិញថ្មី")
    public ProductDto.Res createProduct(ProductDto.Req request) {
        if (productRepository.existsByCode(request.code())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "លេខកូដទំនិញនេះមានរួចហើយ!");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទចំណាត់ថ្នាក់នេះទេ!"));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product.setActive(true);

        if (!request.stockable()) {
            product.setCurrentStock(0);
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    @LogAudit(action = "UPDATE", moduleName = "PRODUCT", entityName = "t_core_product", defaultReason = "កែប្រែព័ត៌មានទំនិញ")
    public ProductDto.Res updateProduct(UUID id, ProductDto.Req request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));

        if (!product.getCode().equals(request.code()) && productRepository.existsByCode(request.code())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "លេខកូដទំនិញនេះមានគេប្រើរួចហើយ!");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទចំណាត់ថ្នាក់នេះទេ!"));

        product.setCode(request.code());
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockable(request.stockable());
        product.setImageUrl(request.imageUrl());

        product.setCategory(category);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductDto.Res getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));
    }

    @Override
    public Page<ProductDto.Res> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    @Override
    public Page<ProductDto.Res> getProductsByCategory(UUID categoryId,Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryIdAndActiveTrue(categoryId,pageable);
        return products.map(productMapper::toResponse);
    }

    @Override
    @Transactional
    @LogAudit(action = "DELETE", moduleName = "PRODUCT", entityName = "t_core_product", defaultReason = "លុបទំនិញ (Soft Delete)")
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));

        product.setActive(false);
        productRepository.save(product);
    }
}