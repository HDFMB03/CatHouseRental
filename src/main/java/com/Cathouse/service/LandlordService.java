package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.LandlordQueryDto;
import com.Cathouse.project.entity.Landlord;
import com.Cathouse.project.ValueObject.LandlordVO;

import java.util.List;

public interface LandlordService extends IService<Landlord> {

    Result<List<LandlordVO>> list(LandlordQueryDto landlordQueryDto);

    Result<String> update(Landlord landlord);

    Result<String> saveEntity(Landlord landlord);

    Result<LandlordVO> listUser(LandlordQueryDto landlordQueryDto);

}
