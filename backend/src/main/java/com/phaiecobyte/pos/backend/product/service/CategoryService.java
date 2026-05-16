package com.phaiecobyte.pos.backend.product.service;

import com.phaiecobyte.pos.backend.product.dto.CategoryReq;
import com.phaiecobyte.pos.backend.product.dto.CategoryRes;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryRes createCategory(CategoryReq request);
    CategoryRes updateCategory(UUID id, CategoryReq request);
    CategoryRes getCategoryById(UUID id);
    List<CategoryRes> getAllCategories();
    void deleteCategory(UUID id); // លុប ឬ បិទ (Soft Delete)
}