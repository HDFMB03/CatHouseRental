package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseOrderInfoQueryDto extends QueryDto {

    private Integer userId;
    private Integer houseId;
    private String orderDate;
    private Integer orderStatus;
    private String orderTimeSplit;
    private List<Integer> houseIds;
}
