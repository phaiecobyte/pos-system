package com.phaiecobyte.pos.backend.core.service.impl;

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
    private final CategoryMapper categoryMapper; // <--- ប្រើ MapStruct

    @Override
    @Transactional
    public CategoryRes createCategory(CategoryReq request) {
        // ១. ពិនិត្យមើលក្រែងលោមានឈ្មោះនេះរួចហើយ
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "ឈ្មោះប្រភេទនេះមានរួចហើយ!");
        }

        // ២. បម្លែង DTO ទៅជា Entity
        Category category = categoryMapper.toEntity(request);
        category.setActive(true);

        // ៣. Save និង បម្លែងត្រឡប់ទៅជា Response DTO វិញ
        category = categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional
    public CategoryRes updateCategory(UUID id, CategoryReq request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទនេះទេ!"));

        // ពិនិត្យបើដូរឈ្មោះ តើឈ្មោះថ្មីនោះជាន់គេឬអត់?
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
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(HttpStatus.NOT_FOUND, "រកមិនឃើញប្រភេទនេះទេ!"));
        
        // ជាទូទៅសម្រាប់ POS យើងធ្វើ Soft Delete (បិទមិនឱ្យប្រើ) ដើម្បីកុំឱ្យបាត់ប្រវត្តិលក់
        category.setActive(false);
        categoryRepository.save(category);
    }
}