package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.philip.lab.pos.mapper.CommonDataMapper;
import com.philip.lab.pos.model.CommonData;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommonDataServiceImpl extends ServiceImpl<CommonDataMapper, CommonData> implements CommonDataService {

    @Override
    public List<CommonData> getByStoreAndName(Long storeId, String name) {
        return this.list(new LambdaQueryWrapper<CommonData>()
                .eq(CommonData::getStoreId, storeId)
                .eq(CommonData::getName, name)
                .orderByAsc(CommonData::getOrder));
    }
}