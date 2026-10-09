package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseSizedEnum {

    LARGE_SINGLE_ROOM(1, "大单间"),
    SMALL_SINGLE_ROOM(2, "小单间"),
    ONE_BEDROOM_ONE_LIVING(3, "一室一厅"),
    TWO_BEDROOM_ONE_LIVING(4, "两室一厅"),
    THREE_BEDROOM_ONE_LIVING(5, "三室一厅"),
    ONE_BEDROOM_TWO_LIVING(6, "一室两厅"),
    TWO_BEDROOM_TWO_LIVING(7, "两室两厅"),
    THREE_BEDROOM_TWO_LIVING(8, "三室两厅"),
    FOUR_BEDROOM_ONE_LIVING(9, "四室一厅"),
    FOUR_BEDROOM_TWO_LIVING(10, "四室两厅"),
    ONE_HOUSE(11, "整栋");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (HouseSizedEnum value : HouseSizedEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
