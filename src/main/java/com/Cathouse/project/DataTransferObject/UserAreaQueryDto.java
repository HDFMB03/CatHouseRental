package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAreaQueryDto extends QueryDto{

    private Integer userId;
    private Integer areaId;
}
