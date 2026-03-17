package com.philip.lab.pos.controller;

import com.philip.lab.pos.dto.Result;
import com.philip.lab.pos.model.OperationLog;
import com.philip.lab.pos.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("/store/{storeId}")
    public Result<List<OperationLog>> getByStore(@PathVariable Long storeId) {
        return Result.success(operationLogService.list(
            new LambdaQueryWrapper<OperationLog>()
                .eq(OperationLog::getStoreId, storeId)
                .orderByDesc(OperationLog::getCreatedAt)
        ));
    }
}