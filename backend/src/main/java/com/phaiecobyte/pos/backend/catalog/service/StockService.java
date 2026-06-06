package com.phaiecobyte.pos.backend.catalog.service;

import com.phaiecobyte.pos.backend.catalog.dto.ProductDto;
import com.phaiecobyte.pos.backend.catalog.dto.StockDto;

public interface StockService {
    ProductDto.Res processStock(StockDto.Req request);
}