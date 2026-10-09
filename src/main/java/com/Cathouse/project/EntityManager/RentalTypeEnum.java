package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentalTypeEnum {

    TOTAL(1, "整租"),
    TOGETHER(2, "合租");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (RentalTypeEnum value : RentalTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
