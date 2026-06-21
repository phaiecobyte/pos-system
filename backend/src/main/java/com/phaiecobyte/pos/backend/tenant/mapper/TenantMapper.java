package com.phaiecobyte.pos.backend.tenant.mapper;

import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.model.Tenant;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    @Mapping(source = "businessType.id", target = "businessTypeId")
    TenantDto.Response toDto(Tenant entity);

    @Mapping(target = "businessType", ignore = true)
    @Mapping(target = "businessType.id", source = "businessTypeId")
    Tenant toEntity(TenantDto.CreateReq dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Tenant tenant, TenantDto.UpdateReq dto);
}