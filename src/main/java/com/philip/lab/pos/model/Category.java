package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("products_category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField("color_code")
    private String colorCode = "#FFFFFF";

    @TableField("display_order")
    private Integer displayOrder = 0;

    @TableField("is_side_dish")
    private boolean isSideDish = false;

    @TableField("store_id")
    private Long storeId;

    @TableField(exist = false)
    private Store store;

    @Override
    public String toString() {
        return this.name;
    }
}