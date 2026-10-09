package com.Cathouse.project.ValueObject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class HouseListItemVO {

    private Integer id;
    private String name;
    private String cover;
    private Double sizeNumber;
    private String communityName;
    private String cityAreaName;
    private Integer directionId;
    private String directionName;
    private Integer fitmentStatusId;
    private String fitmentStatusName;
    private BigDecimal rent;
    private Integer depositMethodId;
    private String depositMethodName;
    private Integer rentalHumen;
    private Integer status;
    private Integer contract;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
