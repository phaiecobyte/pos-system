package com.phaiecobyte.pos.backend.auth.controller;

import com.phaiecobyte.pos.backend.auth.dto.RoleDto;
import com.phaiecobyte.pos.backend.auth.service.RoleService;
import com.phaiecobyte.pos.backend.common.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.phaiecobyte.pos.backend.auth.controller.RoutUrl.role_url;

@RestController
@RequiredArgsConstructor
@RequestMapping(role_url)
public class RoleController {
    private final RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<?> list (){
        var roles = roleService.list();
        return ResponseEntity.ok(ApiResponse.success(roles,"Retrieve role list successfully...!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody RoleDto dto){
        var roles = roleService.create(dto);
        return ResponseEntity.ok(ApiResponse.success(roles,"Create new role successfully...!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> update(@PathVariable(value = "id")UUID id, @Valid @RequestBody RoleDto dto){
        var role = roleService.update(id,dto);
        return ResponseEntity.ok(ApiResponse.success(role,"Update Role successfully...!"));
    }

}
