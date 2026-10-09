package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseFitmentEnum {

    BARE_FLOOR(1, "毛坯房"),
    SIMPLE_DECORATION(2, "简装房"),
    MEDIUM_DECORATION(3, "中装房"),
    LUXURY_DECORATION(4, "精装房"),
    LUXURY_FULL_FURNISHED(5, "豪装房"),
    FULLY_FURNISHED(6, "拎包入住"),
    PARTIAL_DECORATION(7, "局部装修"),
    SOFT_FURNISHING(8, "软装搭配");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (HouseFitmentEnum value : HouseFitmentEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
