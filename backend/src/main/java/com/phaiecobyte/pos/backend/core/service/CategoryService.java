package com.phaiecobyte.pos.backend.core.service;

import com.phaiecobyte.pos.backend.core.dto.CategoryReq;
import com.phaiecobyte.pos.backend.core.dto.CategoryRes;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryRes createCategory(CategoryReq request);
    CategoryRes updateCategory(UUID id, CategoryReq request);
    CategoryRes getCategoryById(UUID id);
    List<CategoryRes> getAllCategories();
    void deleteCategory(UUID id); // លុប ឬ បិទ (Soft Delete)
}