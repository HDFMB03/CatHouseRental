package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.AreaQueryDto;
import com.Cathouse.project.entity.Area;

import java.util.List;

public interface AreaService extends IService<Area> {

    Result<List<Area>> list(AreaQueryDto areaQueryDto);

    Result<String> update(Area area);

    Result<String> saveEntity(Area area);

}
