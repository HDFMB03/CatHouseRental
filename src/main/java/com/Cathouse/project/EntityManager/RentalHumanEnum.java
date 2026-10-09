package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RentalHumanEnum {

    LANDLORDS(1, "房东直租"),
    AGENT(0, "中介代理");

    private final Integer type;
    private final String detail;

}
