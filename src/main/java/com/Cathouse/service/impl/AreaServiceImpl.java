package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.mapper.AreaMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.AreaQueryDto;
import com.Cathouse.project.entity.Area;
import com.Cathouse.service.AreaService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    @Override
    public Result<List<Area>> list(AreaQueryDto areaQueryDto) {
        List<Area> list = this.baseMapper.list(areaQueryDto);
        Integer count = this.baseMapper.listCount(areaQueryDto);
        return ApiResult.success(list, count);
    }

    @Override
    public Result<String> update(Area area) {
        parenDeal(area);
        updateById(area);
        return ApiResult.success("地区绑定成功");
    }

    private void parenDeal(Area area) {
        AssertUtils.hasText(area.getName(), "地区名不能为空");
        // 如果这个名字不合理，不能新增
        AreaQueryDto areaQueryDto = new AreaQueryDto();
        areaQueryDto.setName(area.getName());
        Integer count = this.baseMapper.listCount(areaQueryDto);
        AssertUtils.isTrue(count == 0, "地区名不能为空");
    }

    @Override
    public Result<String> saveEntity(Area area) {
        parenDeal(area);
        save(area);
        return ApiResult.success("地区新增成功");
    }
}
