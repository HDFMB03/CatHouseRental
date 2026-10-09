package com.Cathouse.project.ValueObject;

import lombok.Data;

@Data
public class UserAreaVO {

    private Integer id;
    private Integer topAreaId;
    private String topAreaName;
    private Integer cityAreaId;
    private String cityAreaName;
}
