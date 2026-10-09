package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.AreaQueryDto;
import com.Cathouse.project.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AreaMapper extends BaseMapper<Area> {

    List<Area> list(AreaQueryDto areaQueryDto);

    Integer listCount(AreaQueryDto areaQueryDto);

}
