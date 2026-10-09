package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.HouseOrderStatusQueryDto;
import com.Cathouse.project.entity.HouseOrderStatus;
import com.Cathouse.project.ValueObject.HouseOrderStatusVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseOrderStatusMapper extends BaseMapper<HouseOrderStatus> {

    List<HouseOrderStatusVO> list(HouseOrderStatusQueryDto queryDto);

    Integer listCount(HouseOrderStatusQueryDto queryDto);

}
