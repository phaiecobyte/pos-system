package com.phaiecobyte.pos.backend.product.service;

import com.phaiecobyte.pos.backend.product.dto.ProductDto;
import com.phaiecobyte.pos.backend.product.dto.StockDto;

public interface StockService {
    ProductDto.Res processStock(StockDto.Req request);
}