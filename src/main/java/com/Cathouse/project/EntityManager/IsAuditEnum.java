package com.Cathouse.project.EntityManager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsAuditEnum {

    STATUS_1(true, "已审核"),
    STATUS_2(false, "未审核");

    private final Boolean type;
    private final String detail;

}
