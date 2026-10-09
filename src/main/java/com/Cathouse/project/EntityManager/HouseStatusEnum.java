package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseStatusEnum {

    STATUS_1(1, "待租"),
    STATUS_2(2, "下架");

    private final Integer type;
    private final String detail;

}
