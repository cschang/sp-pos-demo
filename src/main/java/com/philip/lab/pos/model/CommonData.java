package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("products_commondata")
public class CommonData {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField("zh_name")
    private String zhName;

    private String value;

    @TableField("`order`")
    private Integer order = 0;

    @TableField(value = "create_at", fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(value = "update_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateAt;

    @TableField("store_id")
    private Long storeId;

    @TableField(exist = false)
    private Store store;
}