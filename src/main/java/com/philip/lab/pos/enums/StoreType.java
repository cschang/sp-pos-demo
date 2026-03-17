package com.philip.lab.pos.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum StoreType {
    BEVERAGE("BEVERAGE"),
    BREAKFAST("BREAKFAST"),
    HOTPOT("HOTPOT"),
    OTHER("OTHER");

    @EnumValue
    private final String value;

    StoreType(String value) {
        this.value = value;
    }
}