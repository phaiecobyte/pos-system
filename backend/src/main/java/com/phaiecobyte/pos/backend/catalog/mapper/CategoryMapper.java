package com.phaiecobyte.pos.backend.product.mapper;

import com.phaiecobyte.pos.backend.product.dto.CategoryDto;
import com.phaiecobyte.pos.backend.product.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // ប្រាប់ MapStruct ឱ្យបង្កើតជា Spring Bean (@Component)
public interface CategoryMapper {
    CategoryDto.Res toResponse(Category category);
    Category toEntity(CategoryDto.CreateReq request);
}