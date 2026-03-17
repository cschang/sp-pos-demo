package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.philip.lab.pos.model.OperationLog;

public interface OperationLogService extends IService<OperationLog> {
    void log(Long userId, Long storeId, String action, String path, String ip, String payload);
}