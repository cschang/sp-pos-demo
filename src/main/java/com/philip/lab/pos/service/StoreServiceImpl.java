package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.philip.lab.pos.mapper.StoreMapper;
import com.philip.lab.pos.model.Store;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {
    @Override
    public boolean saveWithSlug(Store store) {
        if (store.getSlug() == null || store.getSlug().isEmpty()) {
            store.setSlug(store.getName().toLowerCase().replace(" ", "-"));
        }
        return this.save(store);
    }
}