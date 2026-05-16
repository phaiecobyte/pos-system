package com.phaiecobyte.pos.backend.product.service;

import com.phaiecobyte.pos.backend.product.dto.ProductRes;
import com.phaiecobyte.pos.backend.product.dto.StockReq;

public interface StockService {
    ProductRes processStock(StockReq request);
}