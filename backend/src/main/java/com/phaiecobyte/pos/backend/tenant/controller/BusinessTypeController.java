package com.phaiecobyte.pos.backend.tenant.controller;

import com.phaiecobyte.pos.backend.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.tenant.model.BusinessType;
import com.phaiecobyte.pos.backend.tenant.service.impl.BusinessTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.phaiecobyte.pos.backend.common.base.Constant.CORE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(CORE_URL)
public class BusinessTypeController {
    private final BusinessTypeService service;

    @GetMapping("/businessType/list")
    public ResponseEntity<ApiResponse<List<BusinessType>>> list(){
        var data = service.list();
        return ResponseEntity.status(HttpStatus.OK)
                        .body(ApiResponse.success(data,"Retrieve business type successfully...!"));

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/businessType/create")
    public ResponseEntity<ApiResponse<BusinessType>> create(@RequestBody BusinessType req){
        var data = service.create(req);
        return ResponseEntity.ok(ApiResponse.success(data,"Create business type successfully...!"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/businessType/update")
    public ResponseEntity<ApiResponse<BusinessType>> update(@RequestBody BusinessType req){
        var data = service.update(req);
        return ResponseEntity.ok(ApiResponse.success(data,"Update business type successfully...!"));
    }


}
