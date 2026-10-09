package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseContractEnum {
    CONTRACT_1(1, "待签署"),
    CONTRACT_2(2, "已签署"),
    CONTRACT_3(3, "拒绝签署");

    private final Integer type;
    private final String detail;

    public static HouseContractEnum valueOfType(Integer type) {
        for (HouseContractEnum contract : values()) {
            if (contract.getType().equals(type)) {
                return contract;
            }
        }
        return null;
    }

    public static String getDetailByType(Integer type) {
        HouseContractEnum contract = valueOfType(type);
        return contract != null ? contract.getDetail() : "未知状态";
    }
}
