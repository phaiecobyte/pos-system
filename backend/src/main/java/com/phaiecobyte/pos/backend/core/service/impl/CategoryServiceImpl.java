package com.phaiecobyte.pos.backend.core.service.impl;

import com.phaiecobyte.pos.backend.core.annotation.LogAudit;
import com.phaiecobyte.pos.backend.core.dto.CategoryReq;
import com.phaiecobyte.pos.backend.core.dto.CategoryRes;
import com.phaiecobyte.pos.backend.core.model.Category;
import com.phaiecobyte.pos.backend.core.exception.AppException;
import com.phaiecobyte.pos.backend.core.mapper.CategoryMapper;
import com.phaiecobyte.pos.backend.core.repository.CategoryRepository;
import com.phaiecobyte.pos.backend.core.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    @LogAudit(action = "CREATE", moduleName = "CATEGORY", entityName = "t_core_category", defaultReason = "បង្កើតប្រភេទចំណាត់ថ្នាក់ថ្មី")
    public CategoryRes createCategory(CategoryReq request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "ឈ្មោះប្រភេទនេះមានរួចហើយ!");
        }

        Category category = categoryMapper.toEntity(request);
        category.setActive(true);

        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    @LogAudit(action = "UPDATE", moduleName = "CATEGORY", entityName = "t_core_category", defaultReason = "កែប្រែព័ត៌មានប្រភេទចំណាត់ថ្នាក់")
    public CategoryRes updateCategory(UUID id, CategoryReq request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទនេះទេ!"));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "ឈ្មោះប្រភេទនេះមានរួចហើយ!");
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryRes getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទនេះទេ!"));
    }

    @Override
    public List<CategoryRes> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @LogAudit(action = "DELETE", moduleName = "CATEGORY", entityName = "t_core_category", defaultReason = "លុបប្រភេទចំណាត់ថ្នាក់ (Soft Delete)")
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទនេះទេ!"));

        category.setActive(false);
        categoryRepository.save(category);
    }
}