package com.philip.lab.pos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;

@Data
@TableName("users_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String email;

    @TableField("first_name")
    private String firstName;

    @TableField("last_name")
    private String lastName;

    @TableField("is_staff")
    private boolean isStaff;

    @TableField("is_superuser")
    private boolean isSuperuser;

    @TableField("is_active")
    private boolean isActive;

    @TableField(value = "date_joined", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateJoined;

    private String role;

    @TableField("store_id")
    private Long storeId;

    private String phone;
    private LocalDate birthday;
    private String gender; // M, F, O

    @TableField("total_spent")
    private Integer totalSpent = 0;

    @TableField(exist = false)
    private Store store;

    public String getDisplayName() {
        if (lastName != null && firstName != null) {
            return lastName + firstName;
        }
        return username;
    }
}