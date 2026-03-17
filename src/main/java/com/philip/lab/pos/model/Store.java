package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.*;
import com.philip.lab.pos.enums.StoreType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("store_store")
public class Store {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @TableField("store_type")
    private StoreType storeType = StoreType.HOTPOT;

    private String slug;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return this.name;
    }
}