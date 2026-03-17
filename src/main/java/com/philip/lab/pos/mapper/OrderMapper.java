package com.philip.lab.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.philip.lab.pos.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    @Select("SELECT * FROM store_order WHERE store_id = #{storeId} AND status = #{status} ORDER BY created_at DESC")
    List<Order> selectActiveOrders(@Param("storeId") Long storeId, @Param("status") String status);
}