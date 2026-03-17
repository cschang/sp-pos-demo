package com.philip.lab.pos.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.philip.lab.pos.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Select("SELECT * FROM store_orderitem WHERE order_id = #{orderId}")
    List<OrderItem> selectByOrderId(Long orderId);
}