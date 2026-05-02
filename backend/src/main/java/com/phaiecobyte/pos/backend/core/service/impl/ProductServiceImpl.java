package com.phaiecobyte.pos.backend.core.service.impl;

import com.phaiecobyte.pos.backend.core.dto.ProductReq;
import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.model.Category;
import com.phaiecobyte.pos.backend.core.model.Product;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import com.phaiecobyte.pos.backend.core.mapper.ProductMapper;
import com.phaiecobyte.pos.backend.core.repository.CategoryRepository;
import com.phaiecobyte.pos.backend.core.repository.ProductRepository;
import com.phaiecobyte.pos.backend.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository; // ត្រូវប្រើដើម្បីឆែក Category
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductRes createProduct(ProductReq request) {
        // ១. ឆែកមើលថាតើលេខកូដ (Barcode) នេះមានអ្នកប្រើហើយឬនៅ?
        if (productRepository.existsByCode(request.getCode())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "លេខកូដទំនិញនេះមានរួចហើយ!");
        }

        // ២. ទាញយក Category ពី Database ផ្អែកលើ ID ដែល Frontend បោះមក
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទចំណាត់ថ្នាក់នេះទេ!"));

        // ៣. បម្លែង DTO ទៅ Entity រួចបំពាក់ Category ចូល
        Product product = productMapper.toEntity(request);
        product.setCategory(category);
        product.setActive(true);
        
        // ប្រសិនបើជាទំនិញមិនកាត់ស្តុក (ឧ. កាហ្វេ) យើងកំណត់ស្តុកឱ្យស្មើ 0 ជានិច្ច
        if (!request.isStockable()) {
            product.setCurrentStock(0);
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductRes updateProduct(UUID id, ProductReq request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));

        if (!product.getCode().equals(request.getCode()) && productRepository.existsByCode(request.getCode())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "លេខកូដទំនិញនេះមានគេប្រើរួចហើយ!");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទចំណាត់ថ្នាក់នេះទេ!"));

        // ធ្វើបច្ចុប្បន្នភាពទិន្នន័យ
        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockable(request.isStockable());
        product.setCategory(category);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductRes getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));
    }

    @Override
    public List<ProductRes> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductRes> getProductsByCategory(UUID categoryId) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId).stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញទំនិញនេះទេ!"));
        
        product.setActive(false); // Soft Delete
        productRepository.save(product);
    }
}