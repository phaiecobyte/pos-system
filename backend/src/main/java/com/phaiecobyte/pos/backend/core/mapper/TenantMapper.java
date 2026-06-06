package com.phaiecobyte.pos.backend.core.mapper;

import com.phaiecobyte.pos.backend.core.dto.TenantDto;
import com.phaiecobyte.pos.backend.core.model.Tenant;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    @Mapping(source = "businessType.code", target = "businessTypeCode")
    TenantDto.Response toDto(Tenant entity);

    @Mapping(target = "businessType", ignore = true)
    Tenant toEntity(TenantDto.CreateReq dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Tenant tenant, TenantDto.UpdateReq dto);
}