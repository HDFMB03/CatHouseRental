package com.Cathouse.project.ValueObject;

import com.Cathouse.project.entity.HouseOrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseOrderStatusVO extends HouseOrderStatus {

    private String oldStatusText;
    private String newStatusText;

}
