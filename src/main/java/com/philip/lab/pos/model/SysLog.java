package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_log")
public class SysLog {
    @TableId(type = IdType.AUTO)
    private Integer logId;
    private String operator;
    private String actionDesc;
    private String methodName;
    private String params;
    private String resultStatus;
    private String errorMsg;
    private Long executionTime;
    private LocalDateTime createTime;

}