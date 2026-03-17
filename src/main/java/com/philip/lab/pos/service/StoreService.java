package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.philip.lab.pos.model.Store;

public interface StoreService extends IService<Store> {
    boolean saveWithSlug(Store store);
}