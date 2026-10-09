package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.HouseQueryDto;
import com.Cathouse.project.entity.House;
import com.Cathouse.project.ValueObject.ChartVO;
import com.Cathouse.project.ValueObject.HouseFlowIndexVO;
import com.Cathouse.project.ValueObject.HouseListItemVO;
import com.Cathouse.project.ValueObject.HouseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMapper extends BaseMapper<House> {

    List<HouseListItemVO> list(HouseQueryDto houseQueryDto);

    List<HouseListItemVO> listCreate(HouseQueryDto houseQueryDto);

    Integer listCount(HouseQueryDto houseQueryDto);

    HouseVO getById(@Param(value = "id") Integer id);

    List<Integer> selectIdsByLandlordId(@Param(value = "landlordId") Integer landlordId);

    List<HouseFlowIndexVO> flowIndexList(HouseQueryDto queryDto);

    List<ChartVO> cityHouseRange(@Param(value = "limitCount") Integer limitCount);

    List<Integer> getIds();


}
