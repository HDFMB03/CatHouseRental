package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseTypeEnum {

    FLATS(1, "公寓"),
    COTTAGE(2, "别墅"),
    BUNGALOW(3,"平房"),
    TOWNHOUSE(4, "联排别墅"),
    DETACHED_HOUSE(5, "独立住宅"),
    MARKETABLE_HOUSING(6,"商品房"),
    CUSTOM_HOME(7,"自建房");

    private final Integer type;
    private final String detail;
    public static String getDetail(Integer type) {
        for (HouseTypeEnum value : HouseTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
