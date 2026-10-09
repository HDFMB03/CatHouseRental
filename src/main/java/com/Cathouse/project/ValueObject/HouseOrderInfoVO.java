package com.Cathouse.project.ValueObject;

import com.Cathouse.project.entity.HouseOrderInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class HouseOrderInfoVO extends HouseOrderInfo {

    private String username;
    private String avatar;
    private String houseName;
    private String cover;
    private String text;
    private Integer contract;
}
