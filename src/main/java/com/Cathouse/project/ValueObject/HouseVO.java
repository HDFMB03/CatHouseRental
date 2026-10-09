package com.Cathouse.project.ValueObject;

import com.Cathouse.project.entity.House;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseVO extends House {

    private Integer topAreaId;
    private String landlordName;
    private String landlordAvatar;
    private String communityName;
    private String cityAreaName;
    private String directionName;
    private String fitmentStatusName;
    private String depositMethodName;
    private String typeName;
    private String rentalTypeName;
    private String sizedName;
    private String idcard;
    private String phone;
    private String orderUserName;
    private String orderUserAvatar;
    private String orderUserPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
}
