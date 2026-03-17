package com.philip.lab.pos.controller;

import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.model.Store;
import com.philip.lab.pos.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public Result<List<Store>> getAllStores() {
        return Result.success(storeService.list());
    }

    @PostMapping
    public Result<Boolean> createStore(@RequestBody Store store) {
        return Result.success(storeService.saveWithSlug(store));
    }
}