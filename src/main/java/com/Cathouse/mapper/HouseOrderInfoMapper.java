package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.HouseOrderInfoQueryDto;
import com.Cathouse.project.entity.HouseOrderInfo;
import com.Cathouse.project.ValueObject.HouseOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseOrderInfoMapper extends BaseMapper<HouseOrderInfo> {

    List<Integer> getIdsByHouseId(@Param(value = "houseId") Integer houseId);

    List<HouseOrderInfoVO> list(HouseOrderInfoQueryDto houseOrderInfoQueryDto);

    Integer listCount(HouseOrderInfoQueryDto houseOrderInfoQueryDto);

}
