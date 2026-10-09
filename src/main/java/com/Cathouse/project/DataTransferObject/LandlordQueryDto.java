package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LandlordQueryDto extends QueryDto{

    private Integer userId;
    private Boolean isAudit;
    private String idcard;
}
