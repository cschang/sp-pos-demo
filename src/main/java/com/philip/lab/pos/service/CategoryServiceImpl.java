package com.philip.lab.pos.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.philip.lab.pos.mapper.CategoryMapper;
import com.philip.lab.pos.model.Category;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> getCategoriesByStore(Long storeId) {
        return baseMapper.selectByStoreId(storeId);
    }
}