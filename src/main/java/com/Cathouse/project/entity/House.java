package com.Cathouse.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "house")
public class House {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer landlordId;
    private Integer areaId;
    private Integer communityId;
    private String detail;
    private String cover;
    private String covers;
    private Integer typeId;
    private Double sizeNumber;
    private Integer directionId;
    private String floor;
    private Integer sizedId;
    private BigDecimal rent;
    private Integer depositMethodId;
    private Integer status;
    private Boolean isSubway;
    private Integer subwayLine;
    private Integer fitmentStatusId;
    private Integer rentalType;
    private String livingFacilities;
    private Integer rentalHumen;
    private Integer contract;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
