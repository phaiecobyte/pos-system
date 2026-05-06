package com.phaiecobyte.pos.backend.core.controller;

import com.phaiecobyte.pos.backend.core.base.ApiResponse;
import com.phaiecobyte.pos.backend.core.dto.ProductReq;
import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j // បន្ថែម Logger
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductRes>> createProduct(@Valid @RequestBody ProductReq request) {
        log.info("ទទួលបានសំណើរបង្កើតទំនិញថ្មី: {}", request.getName());
        ProductRes data = productService.createProduct(request);
        // ប្រើប្រាស់ Factory Method ថ្មីរបស់យើង
        return new ResponseEntity<>(
                ApiResponse.success(data, "បង្កើតទំនិញថ្មីបានជោគជ័យ"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<ProductRes>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.debug("ទាញយកទំនិញ ទំព័រទី: {}, ចំនួន: {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]).descending());
        Page<ProductRes> data = productService.getAllProducts(pageable);
        return ResponseEntity.ok(ApiResponse.success(data, "ទាញយកបញ្ជីទំនិញបានជោគជ័យ"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductRes>> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(id), "ទាញយកទំនិញបានជោគជ័យ"));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductRes>>> getProductsByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductsByCategory(categoryId), "ទាញយកទំនិញតាមប្រភេទបានជោគជ័យ"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductRes>> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductReq request) {
        log.info("ធ្វើបច្ចុប្បន្នភាពទំនិញ ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success(productService.updateProduct(id, request), "កែប្រែទំនិញបានជោគជ័យ"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id) {
        log.warn("ស្នើសុំលុបទំនិញ ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "លុបទំនិញបានជោគជ័យ"));
    }
}