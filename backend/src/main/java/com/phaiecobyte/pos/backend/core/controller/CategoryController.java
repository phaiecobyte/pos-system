package com.phaiecobyte.pos.backend.core.controller;

import com.phaiecobyte.pos.backend.core.base.ApiResponse;
import com.phaiecobyte.pos.backend.core.dto.CategoryReq;
import com.phaiecobyte.pos.backend.core.dto.CategoryRes;
import com.phaiecobyte.pos.backend.core.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryRes>> createCategory(@Valid @RequestBody CategoryReq request) {
        CategoryRes data = categoryService.createCategory(request);
        return new ResponseEntity<>(
                ApiResponse.success(data, "បង្កើតប្រភេទចំណាត់ថ្នាក់ថ្មីបានជោគជ័យ!"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CategoryRes>>> getAllCategories() {
        List<CategoryRes> data = categoryService.getAllCategories();
        return ResponseEntity.ok(
                ApiResponse.success(data, "ទាញយកបញ្ជីចំណាត់ថ្នាក់បានជោគជ័យ")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> getCategoryById(@PathVariable UUID id) {
        CategoryRes data = categoryService.getCategoryById(id);
        return ResponseEntity.ok(
                ApiResponse.success(data, "ទាញយកព័ត៌មានចំណាត់ថ្នាក់បានជោគជ័យ")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryReq request) {
        CategoryRes data = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(data, "ធ្វើបច្ចុប្បន្នភាពចំណាត់ថ្នាក់បានជោគជ័យ")
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "បានលុបចំណាត់ថ្នាក់ចេញពីប្រព័ន្ធដោយជោគជ័យ")
        );
    }
}