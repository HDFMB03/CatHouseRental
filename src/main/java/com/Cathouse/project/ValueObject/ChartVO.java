package com.Cathouse.project.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartVO {
    private String name;
    private Object count;
}
