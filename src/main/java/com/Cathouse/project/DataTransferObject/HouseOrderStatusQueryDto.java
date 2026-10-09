package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseOrderStatusQueryDto extends QueryDto{

    private Integer houseOrderInfoId;
}
