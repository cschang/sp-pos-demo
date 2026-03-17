package com.philip.lab.pos.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("orders_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("session_token")
    private String sessionToken;

    @TableField("dining_type")
    private String diningType;

    @TableField("dining_number")
    private String diningNumber;

    @TableField("total_price")
    private Integer totalPrice = 0;

    private String status = "pending";

    private String notes;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField("created_by_id")
    private Long createdById;

    @TableField("store_id")
    private Long storeId;

    @TableField("order_sn")
    private String orderSn;

    @TableField(exist = false)
    private User createdBy;

    @TableField(exist = false)
    private Store store;
}