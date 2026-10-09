package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AreaQueryDto extends QueryDto{

    private Integer parentId;
    private String name;
}
