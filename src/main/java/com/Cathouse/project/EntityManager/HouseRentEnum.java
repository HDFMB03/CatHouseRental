package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HouseRentEnum {

    STATUS_1(1, "0-1000"),
    STATUS_2(2, "1000-1500"),
    STATUS_3(3, "1500-2500"),
    STATUS_4(4, "2500-10000"),
    STATUS_5(5, "10000-15000");
    private final Integer type;
    private final String detail;

}
