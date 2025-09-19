package com.phaiecobyte.possystem.service.impl;

import com.phaiecobyte.possystem.model.Category;
import com.phaiecobyte.possystem.payload.req.CategoryReq;
import com.phaiecobyte.possystem.repository.CategoryRepository;
import com.phaiecobyte.possystem.service.BaseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements BaseService<Category,CategoryReq> {
    private final CategoryRepository repository;
    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Category> getById(long id) {
        return Optional.of(repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Category with id:" + id + " not found!")));
    }


    @Override
    public Category create(CategoryReq req) {
        Category newCategory = new Category();
        newCategory.setName(req.getName());
        newCategory.setDescription(req.getDescription());
        return repository.save(newCategory);
    }

    @Override
    public Category update(Category req) {
        var oldCategory = repository.findById(req.getId())
                .orElseThrow(()->new EntityNotFoundException("Not found"));
        oldCategory.setName(req.getName());
        oldCategory.setDescription(req.getDescription());
        return repository.save(oldCategory);
    }

    @Override
    public void remove(long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
