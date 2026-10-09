package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderInfoQueryDto;
import com.Cathouse.project.entity.HouseOrderInfo;
import com.Cathouse.project.ValueObject.HouseOrderInfoVO;

import java.util.List;

public interface HouseOrderInfoService extends IService<HouseOrderInfo> {

    Result<List<HouseOrderInfoVO>> list(HouseOrderInfoQueryDto houseOrderInfoQueryDto);

    Result<String> saveEntity(HouseOrderInfo houseOrderInfo);

    Result<String> updateEntity(HouseOrderInfo houseOrderInfo);

    Result<List<HouseOrderInfoVO>> listLandlord(HouseOrderInfoQueryDto houseOrderInfoQueryDto);

}
