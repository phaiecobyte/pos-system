package com.phaiecobyte.possystem.controller.api;

import com.phaiecobyte.possystem.model.Order;
import com.phaiecobyte.possystem.payload.req.OrderReq;
import com.phaiecobyte.possystem.service.impl.OrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderApiController extends BaseApiController<Order, OrderReq, OrderServiceImpl> {
    @Autowired
    public OrderApiController(OrderServiceImpl service) {
        super(service);
    }
}
