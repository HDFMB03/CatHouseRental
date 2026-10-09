package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseDepositEnum {

    DEPOSIT_ONE_PAY_ONE(1, "押一付一"),
    DEPOSIT_ONE_PAY_THREE(2, "押一付二"),
    DEPOSIT_TWO_PAY_ONE(3, "押一付三"),
    DEPOSIT_THREE_PAY_ONE(4, "押二付一"),
    DEPOSIT_THREE_PAY_THREE(5, "押二付二"),
    DEPOSIT_FOUR_PAY_ONE(6, "押三付一"),
    DEPOSIT_FOUR_PAY_THREE(7, "押三付二"),
    HALF_YEAR_PAYMENT(14, "半年付"),
    YEAR_PAYMENT(15, "年付"),
    FREE(16,"无押金");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (HouseDepositEnum value : HouseDepositEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
