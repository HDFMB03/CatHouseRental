package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.UserAreaQueryDto;
import com.Cathouse.project.entity.UserArea;
import com.Cathouse.project.ValueObject.UserAreaVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAreaMapper extends BaseMapper<UserArea> {

    List<UserArea> list(UserAreaQueryDto UserAreaQueryDto);

    Integer listCount(UserAreaQueryDto UserAreaQueryDto);

    List<UserAreaVO> listUser(UserAreaQueryDto userAreaQueryDto);

}
