package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseDirectionEnum {

    EAST(1, "正东"),
    WEST(2, "正西"),
    SOUTH(3, "正南"),
    NORTH(4, "正北"),
    SOUTHEAST(5, "东南"),
    NORTHEAST(6, "东北"),
    SOUTHWEST(7, "西南"),
    NORTHWEST(8, "西北"),
    NORTH_FACING_SOUTH(9, "坐北朝南"),
    SOUTH_FACING_NORTH(10, "坐南朝北");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (HouseDirectionEnum value : HouseDirectionEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
