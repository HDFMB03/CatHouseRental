package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderEvaluationsQueryDto;
import com.Cathouse.project.entity.HouseOrderEvaluations;
import com.Cathouse.project.ValueObject.HouseOrderEvaluationsVO;

import java.util.List;

public interface HouseOrderEvaluationsService extends IService<HouseOrderEvaluations> {

    Result<List<HouseOrderEvaluationsVO>> list(HouseOrderEvaluationsQueryDto queryDto);

    Result<String> saveEntity(HouseOrderEvaluations houseOrderEvaluations);

    Result<List<HouseOrderEvaluationsVO>> houseList(Integer houseId);

}
