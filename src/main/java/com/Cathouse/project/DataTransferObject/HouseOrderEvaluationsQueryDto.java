package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseOrderEvaluationsQueryDto extends QueryDto{

    private Integer userId;
    private Integer houseOrderInfoId;
    private List<Integer> houseOrderInfoIds;
}
