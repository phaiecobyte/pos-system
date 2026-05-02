package com.phaiecobyte.pos.backend.core.mapper;

import com.phaiecobyte.pos.backend.core.dto.ProductReq;
import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // ទាញយក id និង name ពី Category Entity មកដាក់ក្នុង ProductResponse
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductRes toResponse(Product product);

    // យើង Ignore category សិន ព្រោះយើងនឹងសរសេរ Logic ទាញ Category ចេញពី Database នៅក្នុង Service Layer
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductReq request);
}