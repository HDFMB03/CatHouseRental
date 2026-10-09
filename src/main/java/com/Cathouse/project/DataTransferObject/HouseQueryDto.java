package com.Cathouse.project.DataTransferObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class HouseQueryDto extends QueryDto {

    private String name;
    private Integer landlordId;
    private Integer areaId;
    private Integer communityId;
    private Integer typeId;
    private Double minSizeNumber;
    private Double maxSizeNumber;
    private Integer directionId;
    private Integer sizedId;
    private BigDecimal minRent;
    private BigDecimal maxRent;
    private Integer depositMethodId;
    private Integer status;
    private Boolean isSubway;
    private Boolean rentalHumen;
    private Integer fitmentStatusId;
    private Integer rentalType;
    private Integer contract;
    private List<Integer> ids;
    private Integer type;
    private Integer days;
}
