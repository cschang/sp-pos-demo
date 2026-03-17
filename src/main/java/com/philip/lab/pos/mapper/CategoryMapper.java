package com.philip.lab.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.philip.lab.pos.model.Category;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("SELECT * FROM store_category WHERE store_id = #{storeId} ORDER BY display_order ASC")
    List<Category> selectByStoreId(Long storeId);
}