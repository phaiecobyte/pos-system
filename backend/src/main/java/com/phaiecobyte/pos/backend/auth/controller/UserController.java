package com.phaiecobyte.pos.backend.auth.controller;

import com.phaiecobyte.pos.backend.auth.dto.AssignRoleReq;
import com.phaiecobyte.pos.backend.auth.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.auth.dto.UserDto;
import com.phaiecobyte.pos.backend.auth.service.UserService;
import com.phaiecobyte.pos.backend.core.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/auth/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> list(Pageable pageable){
        var users = userService.list(pageable);
        return ResponseEntity.ok(ApiResponse.success(users,"Retrieve user successfully...!"));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserReq req){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userService.create(req),"Create user successfully...!"));
    }

    @PatchMapping("/toggle-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleStatus(@PathVariable UUID id){
        return ResponseEntity.ok(ApiResponse.success(userService.toggleUserStatus(id),"Change user status successfully...!"));
    }

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignRoles(
            @PathVariable(value = "id") UUID id,
            @RequestBody AssignRoleReq req
            ){
        var user = userService.assignRole(id,req);
        return ResponseEntity.ok(ApiResponse.success(user,"Assign role to user successfully...!"));
    }
}
