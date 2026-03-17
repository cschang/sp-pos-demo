package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("orders_ordersession")
public class OrderSession {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("store_id")
    private Long storeId;

    private String token = UUID.randomUUID().toString();

    @TableField("is_claimed")
    private boolean isClaimed = false;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(exist = false)
    private Store store;

    public boolean isValid() {
        if (this.createdAt == null) return false;
        return !this.isClaimed && LocalDateTime.now().isBefore(this.createdAt.plusHours(2));
    }
}