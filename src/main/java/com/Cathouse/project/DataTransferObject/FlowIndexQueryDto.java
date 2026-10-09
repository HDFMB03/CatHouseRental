package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FlowIndexQueryDto extends QueryDto {

    private Integer type;
    private String contentType;
    private Integer contentId;
    private Integer userId;
}
