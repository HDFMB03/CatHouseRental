package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.UserAreaMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.UserAreaQueryDto;
import com.Cathouse.project.entity.UserArea;
import com.Cathouse.project.ValueObject.UserAreaVO;
import com.Cathouse.service.UserAreaService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAreaServiceImpl extends ServiceImpl<UserAreaMapper, UserArea> implements UserAreaService {

    @Override
    public Result<List<UserArea>> list(UserAreaQueryDto userAreaQueryDto) {
        List<UserArea> list = this.baseMapper.list(userAreaQueryDto);
        Integer count = this.baseMapper.listCount(userAreaQueryDto);
        return ApiResult.success(list, count);
    }

    @Override
    public Result<String> update(UserArea userArea) {
        updateById(userArea);
        return ApiResult.success("常居住地信息修改成功");
    }

    @Override
    public Result<String> saveEntity(UserArea userArea) {
        UserAreaQueryDto UserAreaQueryDto = new UserAreaQueryDto();
        UserAreaQueryDto.setUserId(LocalThreadHolder.getUserId());
        Integer count = this.baseMapper.listCount(UserAreaQueryDto);
        AssertUtils.isTrue(count == 0, "请不要重复设置常居住地");
        userArea.setUserId(LocalThreadHolder.getUserId());
        save(userArea);
        return ApiResult.success("常居住地信息设置成功");
    }

    @Override
    public Result<List<UserAreaVO>> listUser(UserAreaQueryDto userAreaQueryDto) {
        List<UserAreaVO> list = this.baseMapper.listUser(userAreaQueryDto);
        return ApiResult.success(list);
    }
}
