package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.CommunityQueryDto;
import com.Cathouse.project.entity.Community;
import com.Cathouse.project.ValueObject.CommunityVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityMapper extends BaseMapper<Community> {

    List<CommunityVO> list(CommunityQueryDto communityQueryDto);

    Integer listCount(CommunityQueryDto communityQueryDto);

}
