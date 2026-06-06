package com.phaiecobyte.pos.backend.product.service;

import com.phaiecobyte.pos.backend.product.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto.Res createCategory(CategoryDto.CreateReq request);
    CategoryDto.Res updateCategory(UUID id, CategoryDto.CreateReq request);
    CategoryDto.Res getCategoryById(UUID id);
    List<CategoryDto.Res> getAllCategories();
    void deleteCategory(UUID id); // លុប ឬ បិទ (Soft Delete)
}