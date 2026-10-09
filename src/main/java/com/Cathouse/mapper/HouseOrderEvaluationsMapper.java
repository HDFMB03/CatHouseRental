package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.HouseOrderEvaluationsQueryDto;
import com.Cathouse.project.entity.HouseOrderEvaluations;
import com.Cathouse.project.ValueObject.HouseOrderEvaluationsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseOrderEvaluationsMapper extends BaseMapper<HouseOrderEvaluations> {

    List<HouseOrderEvaluationsVO> list(HouseOrderEvaluationsQueryDto queryDto);

    Integer listCount(HouseOrderEvaluationsQueryDto queryDto);

}
