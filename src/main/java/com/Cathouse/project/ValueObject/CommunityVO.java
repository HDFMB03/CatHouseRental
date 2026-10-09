package com.Cathouse.project.ValueObject;

import com.Cathouse.project.entity.Community;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class CommunityVO extends Community {

    private Integer topAreaId;
    private String topAreaName;
    private String cityAreaName;
}
