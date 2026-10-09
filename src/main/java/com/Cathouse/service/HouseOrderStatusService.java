package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderStatusQueryDto;
import com.Cathouse.project.entity.HouseOrderStatus;
import com.Cathouse.project.ValueObject.HouseOrderStatusVO;

import java.util.List;

public interface HouseOrderStatusService extends IService<HouseOrderStatus> {

    Result<List<HouseOrderStatusVO>> list(HouseOrderStatusQueryDto queryDto);

    Result<String> saveEntity(HouseOrderStatus houseOrderStatus);

}
