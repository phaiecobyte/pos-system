package com.phaiecobyte.possystem.controller.api;

import com.phaiecobyte.possystem.model.Category;
import com.phaiecobyte.possystem.payload.req.CategoryReq;
import com.phaiecobyte.possystem.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "Category management endpoints")
@SecurityRequirement(name = "keycloak")
public class CategoryApiController extends BaseApiController<Category,CategoryReq, CategoryServiceImpl> {
    @Autowired
    public CategoryApiController(CategoryServiceImpl categoryService) {
        super(categoryService);
    }
}