package com.phaiecobyte.pos.backend.tenant.mapper;

import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.entity.Tenant;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

// componentModel = "spring" អនុញ្ញាតឱ្យយើង Inject ប្រើប្រាស់វាជា Spring Bean នៅក្នុង Service បានយ៉ាងងាយស្រួល
@Mapper(componentModel = "spring")
public interface TenantMapper {
    
    // បម្លែងពី Entity ទៅ Record DTO
    TenantDto.Response toDto(Tenant entity);
    
    // បម្លែងពី Record DTO ទៅ Entity វិញ
    Tenant toEntity(TenantDto.CreateReq dto);

    //// NullValuePropertyMappingStrategy.IGNORE គឺដើម្បីការពារ៖ បើ DTO បញ្ជូនមក null វានឹងរក្សាតម្លៃចាស់ក្នុង Entity ទុកដដែល។
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Tenant tenant, TenantDto.UpdateReq dto);

}