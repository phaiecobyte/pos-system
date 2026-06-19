package com.phaiecobyte.pos.backend.tenant.controller;

import com.phaiecobyte.pos.backend.core.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("core/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Object> listTenants(
            @ParameterObject Pageable pageable
    ) {
        var tenants = tenantService.list(pageable);
        return ResponseEntity.ok(ApiResponse.success(tenants, "Tenants retrieved successfully!"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") UUID id){
        var tenant = tenantService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(tenant,"Retrieve tenant by id successfully...!"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createTenant(@RequestBody TenantDto.CreateReq tenantReq) {
        var newTenant = tenantService.create(tenantReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(newTenant, "New Tenant created successfully!"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id")UUID id,
            @Valid @RequestBody TenantDto.UpdateReq req
            ){
        var tenant = tenantService.update(id,req);
        return ResponseEntity.ok(ApiResponse.success(tenant,"Update tenant successfully...!"));
    }
}