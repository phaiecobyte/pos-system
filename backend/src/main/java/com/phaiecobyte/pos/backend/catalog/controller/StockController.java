package com.phaiecobyte.pos.backend.catalog.controller;

import com.phaiecobyte.pos.backend.core.common.base.ApiResponse;
import com.phaiecobyte.pos.backend.catalog.dto.ProductDto;
import com.phaiecobyte.pos.backend.catalog.dto.StockDto;
import com.phaiecobyte.pos.backend.catalog.service.StockService;
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
@RequestMapping("/core/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/process")
    public ResponseEntity<ApiResponse<ProductDto.Res>> processStock(@Valid @RequestBody StockDto.Req request) {
        log.info("ទទួលបានប្រតិបត្តិការស្តុក: {} សម្រាប់ទំនិញ ID: {}", request.type(), request.productId());
        ProductDto.Res data = stockService.processStock(request);
        return ResponseEntity.ok(ApiResponse.success(data, "ប្រតិបត្តិការស្តុកជោគជ័យ"));
    }
}