package com.phaiecobyte.pos.backend.catalog.mapper;

import com.phaiecobyte.pos.backend.catalog.dto.CategoryDto;
import com.phaiecobyte.pos.backend.catalog.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // ប្រាប់ MapStruct ឱ្យបង្កើតជា Spring Bean (@Component)
public interface CategoryMapper {
    CategoryDto.Res toResponse(Category category);
    Category toEntity(CategoryDto.CreateReq request);
}