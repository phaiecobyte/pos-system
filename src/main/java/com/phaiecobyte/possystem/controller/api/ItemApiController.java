package com.phaiecobyte.possystem.controller.api;

import com.phaiecobyte.possystem.model.Item;
import com.phaiecobyte.possystem.payload.req.ItemReq;
import com.phaiecobyte.possystem.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/items")
public class ItemApiController extends BaseApiController<Item, ItemReq, ItemServiceImpl>{
    @Autowired
    public ItemApiController(ItemServiceImpl itemService) {
        super(itemService);
    }
}
