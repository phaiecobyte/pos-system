package com.phaiecobyte.pos.backend.product.controller;

import com.phaiecobyte.pos.backend.core.base.ApiResponse;
import com.phaiecobyte.pos.backend.product.dto.CategoryDto;
import com.phaiecobyte.pos.backend.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("core/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('CREATE_CATEGORY')")
    @PostMapping("/create")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody CategoryDto.CreateReq request) {
        CategoryDto.Res data = categoryService.createCategory(request);
        return new ResponseEntity<>(
                ApiResponse.success(data, "បង្កើតប្រភេទចំណាត់ថ្នាក់ថ្មីបានជោគជ័យ!"),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getAllCategories() {
        List<CategoryDto.Res> data = categoryService.getAllCategories();
        return ResponseEntity.ok(
                ApiResponse.success(data, "ទាញយកបញ្ជីចំណាត់ថ្នាក់បានជោគជ័យ")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable UUID id) {
        CategoryDto.Res data = categoryService.getCategoryById(id);
        return ResponseEntity.ok(
                ApiResponse.success(data, "ទាញយកព័ត៌មានចំណាត់ថ្នាក់បានជោគជ័យ")
        );
    }

    @PreAuthorize("hasAuthority('UPDATE_CATEGORY')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryDto.CreateReq request) {
        CategoryDto.Res data = categoryService.updateCategory(id, request);
        return ResponseEntity.ok(
                ApiResponse.success(data, "ធ្វើបច្ចុប្បន្នភាពចំណាត់ថ្នាក់បានជោគជ័យ")
        );
    }

    @PreAuthorize("hasAnyAuthority('DELETE_CATEGORY')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                ApiResponse.success(null, "បានលុបចំណាត់ថ្នាក់ចេញពីប្រព័ន្ធដោយជោគជ័យ")
        );
    }
}