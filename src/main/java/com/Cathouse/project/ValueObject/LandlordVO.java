package com.Cathouse.project.ValueObject;

import com.Cathouse.project.entity.Landlord;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class LandlordVO extends Landlord {

    private String avatar;
    private String username;
}
