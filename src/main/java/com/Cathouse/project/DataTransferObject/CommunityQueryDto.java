package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommunityQueryDto extends QueryDto {

    private Integer areaId;
    private String name;
}
