package com.Cathouse.project.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivingFacilityVO {
    private String label;
    private Boolean selected;
}
