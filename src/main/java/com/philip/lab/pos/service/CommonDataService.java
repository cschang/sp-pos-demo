package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.philip.lab.pos.model.CommonData;

import java.util.List;

public interface CommonDataService extends IService<CommonData> {
    List<CommonData> getByStoreAndName(Long storeId, String name);
}