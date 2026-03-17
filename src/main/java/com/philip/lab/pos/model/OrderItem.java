package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@TableName("orders_orderitem")
public class OrderItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private Long orderId;

    @TableField("product_id")
    private Long productId;

    @TableField("product_name")
    private String productName;

    private Integer price = 0;

    private Integer quantity = 1;

    private String spicy;

    @TableField("is_side_dish")
    private boolean isSideDish = false;

    @TableField("attached_to_id")
    private Long attachedToId;

    @TableField(exist = false)
    private List<OrderItem> sideDishes;
}