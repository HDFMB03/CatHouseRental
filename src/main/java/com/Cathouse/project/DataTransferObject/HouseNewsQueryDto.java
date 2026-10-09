package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseNewsQueryDto extends QueryDto{

    private String title;
    private List<Integer> ids;
}
