package com.phaiecobyte.pos.backend.core.common.base;

import java.util.UUID;

public class Constant {
    //================== PRODUCT MODULE =====================
    public static final String CORE_URL = "core/api/v1";
    public static final String PRODUCT_URL = "core/api/v1/products";
    public static final String STOCK_URL = "core/api/v1/stocks";


    //===================== DEFAULT TENANT ID ================
    public static final UUID tenantRestaurant = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342ca");
    public static final UUID tenantCosmetics = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342cb");
}
