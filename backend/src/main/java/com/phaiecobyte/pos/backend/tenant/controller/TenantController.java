package com.phaiecobyte.pos.backend.tenant.controller;

import com.phaiecobyte.pos.backend.core.base.ApiResponse;
import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sys/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    // មានតែអ្នកមានសិទ្ធិ SUPER_ADMIN ប៉ុណ្ណោះទើបអាចមើលបញ្ជីហាងទាំងអស់បាន
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Object> listTenants(
            Pageable pageable
    ) {
        var tenants = tenantService.list(pageable);
        return ResponseEntity.ok(ApiResponse.success(tenants, "Tenants retrieved successfully!"));
    }

    // API សម្រាប់បង្កើតហាងថ្មីឱ្យអតិថិជន
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createTenant(@RequestBody TenantDto.CreateReq tenantReq) {
        var newTenant = tenantService.create(tenantReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(newTenant, "New Tenant created successfully!"));
    }
}