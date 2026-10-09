package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.HouseNewsQueryDto;
import com.Cathouse.project.entity.HouseNews;
import com.Cathouse.project.ValueObject.HouseNewsListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HouseNewsMapper extends BaseMapper<HouseNews> {

    List<Integer> getIds();

    List<HouseNewsListVO> list(HouseNewsQueryDto houseNewsQueryDto);

    Integer listCount(HouseNewsQueryDto houseNewsQueryDto);

}
