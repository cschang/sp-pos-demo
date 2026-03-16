package com.philip.lab.pos.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philip.lab.pos.mapper.SysLogMapper;
import com.philip.lab.pos.model.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    ObjectMapper objectMapper;
    @Async("logExecutor")
    public void saveLog(SysLog sysLog, Object[] args) {
        try {

            String jsonParams = objectMapper.writeValueAsString(args);
            sysLog.setParams(jsonParams);
            // 測試非同步
            Thread.sleep(2000);
            sysLogMapper.insert(sysLog);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            sysLog.setParams("JSON 轉換出錯: " + Arrays.toString(args));
            sysLogMapper.insert(sysLog);
            e.printStackTrace();
        }
    }
}