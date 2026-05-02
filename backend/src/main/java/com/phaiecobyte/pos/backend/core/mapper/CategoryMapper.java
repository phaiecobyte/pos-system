package com.phaiecobyte.pos.backend.core.mapper;

import com.phaiecobyte.pos.backend.core.dto.CategoryReq;
import com.phaiecobyte.pos.backend.core.dto.CategoryRes;
import com.phaiecobyte.pos.backend.core.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // ប្រាប់ MapStruct ឱ្យបង្កើតជា Spring Bean (@Component)
public interface CategoryMapper {
    CategoryRes toResponse(Category category);
    Category toEntity(CategoryReq request);
}