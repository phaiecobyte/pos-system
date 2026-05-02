package com.phaiecobyte.pos.backend.core.service;

import com.phaiecobyte.pos.backend.core.dto.ProductRes;
import com.phaiecobyte.pos.backend.core.dto.StockReq;

public interface StockService {
    ProductRes processStock(StockReq request);
}