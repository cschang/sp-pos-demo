package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.philip.lab.pos.mapper.OperationLogMapper;
import com.philip.lab.pos.model.OperationLog;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

    @Override
    public void log(Long userId, Long storeId, String action, String path, String ip, String payload) {
        OperationLog log = new OperationLog();
        log.setUserId(userId);
        log.setStoreId(storeId);
        log.setAction(action);
        log.setPath(path);
        log.setIpAddress(ip);
        log.setPayload(payload);
        this.save(log);
    }
}