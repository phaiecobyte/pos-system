package com.phaiecobyte.pos.backend.catalog.controller;

import com.phaiecobyte.pos.backend.core.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.catalog.dto.ProductDto;
import com.phaiecobyte.pos.backend.catalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/core/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "API សម្រាប់ការគ្រប់គ្រងទិន្នន័យទំនិញ និងមុខម្ហូប") // ធ្វើឱ្យ API ចេញជាក្រុមច្បាស់លាស់
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('CREATE_PRODUCT')")
    @PostMapping("/create")
    @Operation(summary = "បង្កើតទំនិញថ្មី", operationId = "createProduct")
    public ResponseEntity<ApiResponse<ProductDto.Res>> createProduct(@Valid @RequestBody ProductDto.Req request) {
        log.info("ទទួលបានសំណើរបង្កើតទំនិញថ្មី: {}", request.name());
        ProductDto.Res response = productService.createProduct(request);
        return ResponseEntity.ok(ApiResponse.success(response, "បង្កើតទំនិញបានជោគជ័យ"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ទាញយកទំនិញតាម ID", operationId = "getProductById")
    public ResponseEntity<ApiResponse<ProductDto.Res>> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(id), "ទាញយកទំនិញបានជោគជ័យ"));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "ទាញយកបញ្ជីទំនិញតាម Category (មាន Pagination)", operationId = "getProductsByCategory")
    public ResponseEntity<ApiResponse<Page<ProductDto.Res>>> getProductsByCategory(
            @PathVariable UUID categoryId,
            @ParameterObject Pageable pageable) { // @ParameterObject នេះអ្នកសរសេរត្រូវហើយ! វាជួយបំបែក Pageable ជា Query Params
        log.debug("ទាញយកទំនិញ ទំព័រទី: {}, ចំនួន: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<ProductDto.Res> data = productService.getProductsByCategory(categoryId, pageable);
        return ResponseEntity.ok(ApiResponse.success(data, "ទាញយកបញ្ជីទំនិញបានជោគជ័យ"));
    }

    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    @PutMapping("update/{id}")
    @Operation(summary = "កែប្រែទិន្នន័យទំនិញ", operationId = "updateProduct")
    public ResponseEntity<ApiResponse<ProductDto.Res>> updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductDto.Req request) {
        log.info("ធ្វើបច្ចុប្បន្នភាពទំនិញ ID: {}", id);
        return ResponseEntity.ok(ApiResponse.success(productService.updateProduct(id, request), "កែប្រែទំនិញបានជោគជ័យ"));
    }

    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "លុបទំនិញ", operationId = "deleteProduct")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id) {
        log.warn("ស្នើសុំលុបទំនិញ ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "លុបទំនិញបានជោគជ័យ"));
    }
}