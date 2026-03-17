package com.philip.lab.pos.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum Role {
    OWNER("OWNER"),
    STAFF("STAFF"),
    CUSTOMER("CUSTOMER");

    @EnumValue // 代表存入資料庫的值
    private final String value;

    Role(String value) {
        this.value = value;
    }
}