package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.philip.lab.pos.model.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getCategoriesByStore(Long storeId);
}