package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.UserAreaQueryDto;
import com.Cathouse.project.entity.UserArea;
import com.Cathouse.project.ValueObject.UserAreaVO;

import java.util.List;

public interface UserAreaService extends IService<UserArea> {

    Result<List<UserArea>> list(UserAreaQueryDto userAreaQueryDto);

    Result<String> update(UserArea userArea);

    Result<String> saveEntity(UserArea userArea);

    Result<List<UserAreaVO>> listUser(UserAreaQueryDto userAreaQueryDto);


}
