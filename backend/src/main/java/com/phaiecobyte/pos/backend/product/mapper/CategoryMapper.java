package com.phaiecobyte.pos.backend.product.mapper;

import com.phaiecobyte.pos.backend.product.dto.CategoryReq;
import com.phaiecobyte.pos.backend.product.dto.CategoryRes;
import com.phaiecobyte.pos.backend.product.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // ប្រាប់ MapStruct ឱ្យបង្កើតជា Spring Bean (@Component)
public interface CategoryMapper {
    CategoryRes toResponse(Category category);
    Category toEntity(CategoryReq request);
}