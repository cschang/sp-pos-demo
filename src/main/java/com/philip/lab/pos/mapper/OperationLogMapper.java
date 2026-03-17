package com.philip.lab.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.philip.lab.pos.model.OperationLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}