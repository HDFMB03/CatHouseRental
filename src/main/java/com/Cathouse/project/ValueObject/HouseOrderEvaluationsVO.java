package com.Cathouse.project.ValueObject;

import com.Cathouse.project.entity.HouseOrderEvaluations;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseOrderEvaluationsVO extends HouseOrderEvaluations {

    private String username;
    private String avatar;
}
