package com.phaiecobyte.pos.backend.core.controller;

import com.phaiecobyte.pos.backend.core.base.ApiResponse;
import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.dto.StockReq;
import com.phaiecobyte.pos.backend.core.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponse<ProductRes>> processStock(@Valid @RequestBody StockReq request) {
        log.info("ទទួលបានប្រតិបត្តិការស្តុក: {} សម្រាប់ទំនិញ ID: {}", request.getType(), request.getProductId());
        ProductRes data = stockService.processStock(request);
        return ResponseEntity.ok(ApiResponse.success(data, "ប្រតិបត្តិការស្តុកជោគជ័យ"));
    }
}