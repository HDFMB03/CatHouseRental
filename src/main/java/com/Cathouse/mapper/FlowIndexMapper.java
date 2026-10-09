package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.FlowIndexQueryDto;
import com.Cathouse.project.entity.FlowIndex;
import com.Cathouse.project.ValueObject.ScoreVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FlowIndexMapper extends BaseMapper<FlowIndex> {

    List<ScoreVO> getScores(FlowIndexQueryDto flowIndexQueryDto);

    List<FlowIndex> list(FlowIndexQueryDto flowIndexQueryDto);

    Integer listCount(FlowIndexQueryDto flowIndexQueryDto);

}
