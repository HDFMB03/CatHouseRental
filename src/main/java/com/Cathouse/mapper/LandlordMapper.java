package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.LandlordQueryDto;
import com.Cathouse.project.entity.Landlord;
import com.Cathouse.project.ValueObject.LandlordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LandlordMapper extends BaseMapper<Landlord> {

    List<LandlordVO> list(LandlordQueryDto landlordQueryDto);

    Integer listCount(LandlordQueryDto landlordQueryDtos);

}
