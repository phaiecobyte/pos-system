package com.phaiecobyte.possystem.service.impl;

import com.phaiecobyte.possystem.model.Category;
import com.phaiecobyte.possystem.model.Item;
import com.phaiecobyte.possystem.payload.req.ItemReq;
import com.phaiecobyte.possystem.repository.CategoryRepository;
import com.phaiecobyte.possystem.repository.ItemRepository;
import com.phaiecobyte.possystem.service.BaseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements BaseService<Item, ItemReq> {
    private final ItemRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Item> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<Item> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Item> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public Item create(ItemReq req) {
        Category category = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Item newItem = new Item();
        newItem.setCategory(category);
        newItem.setName(req.getName());
        newItem.setPriceIn(req.getPriceIn());
        newItem.setPriceOut(req.getPriceOut());
        newItem.setStock(req.getStock());
        newItem.setDescription(req.getDescription());
        return repository.save(newItem);
    }

    @Override
    public Item update(Item itemReq) {
        Item existing = repository.findById(itemReq.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        existing.setName(itemReq.getName());
        existing.setPriceIn(itemReq.getPriceIn());
        existing.setPriceOut(itemReq.getPriceOut());
        existing.setStock(itemReq.getStock());
        existing.setDescription(itemReq.getDescription());

        if (itemReq.getCategory() != null && itemReq.getCategory().getId() != 0) {
            Category category = categoryRepository.findById(itemReq.getCategory().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existing.setCategory(category);
        }

        return repository.save(existing);
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
