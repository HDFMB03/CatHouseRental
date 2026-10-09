package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseOrderStatusEnum {

    STATUS_1(1, "预约中"),
    STATUS_2(2, "预约成功"),
    STATUS_3(3, "预约失败"),
    STATUS_4(4, "已取消预约"),
    STATUS_5(5, "服务已完成");

    private final Integer type;
    private final String detail;

    public static String getDetail(Integer type) {
        for (HouseOrderStatusEnum value : HouseOrderStatusEnum.values()) {
            if (value.getType().equals(type)) {
                return value.getDetail();
            }
        }
        return "其他";
    }

}
