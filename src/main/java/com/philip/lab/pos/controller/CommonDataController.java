package com.philip.lab.pos.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.model.CommonData;
import com.philip.lab.pos.service.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/common-data")
public class CommonDataController {

    @Autowired
    private CommonDataService commonDataService;

    @GetMapping("/store/{storeId}")
    public Result<List<CommonData>> getByStore(@PathVariable Long storeId, @RequestParam(required = false) String name) {
        if (name != null) {
            return Result.success(commonDataService.getByStoreAndName(storeId, name));
        }
        return Result.success(commonDataService.list(new LambdaQueryWrapper<CommonData>()
                .eq(CommonData::getStoreId, storeId)
                .orderByAsc(CommonData::getOrder)));
    }

    @PostMapping
    public Result<Boolean> saveOrUpdate(@RequestBody CommonData commonData) {
        return Result.success(commonDataService.saveOrUpdate(commonData));
    }
}