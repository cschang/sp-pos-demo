package com.philip.lab.pos.aspect;

import com.philip.lab.pos.annotation.Log;
import com.philip.lab.pos.model.SysLog;
import com.philip.lab.pos.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogService logService;


    public void serviceStatistics() {}

    @Around("@annotation(logAnnotation)")
    public Object logToDb(ProceedingJoinPoint joinPoint, Log logAnnotation) throws Throwable {
        SysLog sysLog = new SysLog();
        String methodName = joinPoint.getSignature().getName();

        sysLog.setOperator("Admin");
        sysLog.setMethodName(methodName);
        sysLog.setParams(Arrays.toString(joinPoint.getArgs()));
        sysLog.setCreateTime(LocalDateTime.now());

        if (methodName.startsWith("save")) sysLog.setActionDesc("新增或更新操作");
        else if (methodName.startsWith("delete") || methodName.startsWith("remove")) sysLog.setActionDesc("刪除操作");
        else if (methodName.startsWith("update")) sysLog.setActionDesc("更新操作");
        else sysLog.setActionDesc("系統寫入操作");

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            sysLog.setResultStatus("SUCCESS");
            return result;
        } catch (Throwable e) {
            sysLog.setResultStatus("FAIL");
            sysLog.setErrorMsg(e.getMessage());
            throw e;
        } finally {
            sysLog.setExecutionTime(System.currentTimeMillis() - start);
            logService.saveLog(sysLog,joinPoint.getArgs());
            logger.info("Auto Log Saved: {} | Method: {} | Status: {}",
                    sysLog.getActionDesc(), methodName, sysLog.getResultStatus());
        }
    }
}