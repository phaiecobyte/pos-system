package com.phaiecobyte.pos.backend.core.controller;

import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.dto.StockReq;
import com.phaiecobyte.pos.backend.core.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping("/process")
    public ResponseEntity<ProductRes> processStock(@Valid @RequestBody StockReq request) {
        // យើង Return ProductResponse ត្រឡប់ទៅវិញ ដើម្បីអោយ Angular ដឹងពីចំនួនស្តុកថ្មីភ្លាមៗ
        return ResponseEntity.ok(stockService.processStock(request));
    }
}