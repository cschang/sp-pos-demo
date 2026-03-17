package com.philip.lab.pos.controller;

import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.model.Category;
import com.philip.lab.pos.model.Store;
import com.philip.lab.pos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/store/{storeId}")
    public Result<List<Category>> getByStore(@PathVariable Long storeId) {
        return Result.success(categoryService.getCategoriesByStore(storeId));
    }

    @PostMapping
    public Result<Boolean> create(@RequestBody Category category) {
        return Result.success(categoryService.save(category));
    }
    @GetMapping
    public Result<List<Category>> getAllStores() {
        return Result.success(categoryService.list());
    }

}