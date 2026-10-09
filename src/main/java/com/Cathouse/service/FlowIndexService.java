package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.FlowIndexQueryDto;
import com.Cathouse.project.entity.FlowIndex;
import com.Cathouse.project.ValueObject.HouseListItemVO;
import com.Cathouse.project.ValueObject.HouseNewsListVO;

import java.util.List;

public interface FlowIndexService extends IService<FlowIndex> {

    Result<List<FlowIndex>> list(FlowIndexQueryDto flowIndexQueryDto);

    Result<String> saveEntity(FlowIndex flowIndex);

    Result<String> viewOperation(FlowIndex flowIndex);

    Result<String> saveOperation(FlowIndex flowIndex);

    Result<String> stayOperation(FlowIndex flowIndex);

    Result<List<HouseListItemVO>> saveListHouse(FlowIndexQueryDto flowIndexQueryDto);

    Result<List<HouseNewsListVO>> saveListHouseNews(FlowIndexQueryDto flowIndexQueryDto);


}
