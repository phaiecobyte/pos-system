package com.phaiecobyte.possystem.controller.api;

import com.phaiecobyte.possystem.payload.res.ApiResponse;
import com.phaiecobyte.possystem.service.BaseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Validated
@SecurityRequirement(name = "keycloak")
public abstract class BaseApiController<T,REQ, S extends BaseService<T, REQ>> {
    protected final S service;

    @GetMapping
    public ResponseEntity<Object> getAll(){
        log.info("Retrieved data successfully!");
        return ResponseEntity.ok(ApiResponse.success("Retrieved data successfully!",service.getAll()));
    }
    @GetMapping("/pagination")
    public ResponseEntity<Object> getAll(Pageable pageable){
        log.info("Retrieved data with pagination successfully!");
        return ResponseEntity.ok(ApiResponse.success("Retrieved data with pagination successfully!",service.getAll(pageable)));
    }
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable long id){
        log.info("Fetch data by id successfully!");
        return ResponseEntity.ok(ApiResponse.success("Fetch data by id successfully!",service.getById(id)));
    }
    @PostMapping
    public  ResponseEntity<ApiResponse<T>> create(@Valid @RequestBody REQ req) {
        log.info("Intercept create request success!");
        T createdEntity = service.create(req);
        log.info("Completed create operation!");
        return ResponseEntity.ok(ApiResponse.success("Insert data successfully!", createdEntity));
    }
    @PutMapping()
    public ResponseEntity<Object> update(@Valid @RequestBody T req){
        log.info("Intercept update request success!");
        T updatedEntity = service.update(req);
        log.info("Completed update operation!");
        return ResponseEntity.ok(ApiResponse.success("Update data successfully!",updatedEntity));
    }
    @GetMapping("/count")
    public long getCount(){
        return service.count();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        log.info("Delete operation completed successfully!");
        service.remove(id);
        return ResponseEntity.ok(ApiResponse.success("Delete operation completed successfully!",null));
    }
}