package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseQueryDto;
import com.Cathouse.project.entity.House;
import com.Cathouse.project.ValueObject.*;

import java.util.List;

public interface HouseService extends IService<House> {

    Result<List<HouseListItemVO>> list(HouseQueryDto houseQueryDto);

    Result<String> update(House house);

    Result<String> saveEntity(House house);

    Result<List<SelectedVO>> houseTypeList();

    Result<List<SelectedVO>> houseDirectionList();

    Result<List<SelectedVO>> houseSizedList();

    Result<List<SelectedVO>> houseDepositMethodList();

    Result<List<SelectedVO>> houseSubwayList();

    Result<List<SelectedVO>> houseFitmentStatusList();

    Result<List<SelectedVO>> houseRentalTypeList();

    Result<List<LivingFacilityVO>> houseLivingFacilityList();

    Result<List<HouseListItemVO>> landlordHouseList(HouseQueryDto houseQueryDto);

    Result<String> houseStatusDeal(Integer id);

    Result<HouseVO> selectById(Integer id);

    Result<List<SelectedVO>> houseSizeNumber();

    Result<List<SelectedVO>> houseRentRange();

    Result<List<HouseFlowIndexVO>> listFlowIndex(HouseQueryDto houseQueryDto);

    Result<List<HouseListItemVO>> listUser(HouseQueryDto houseQueryDto);

    Result<List<ChartVO>> listChart(HouseQueryDto houseQueryDto);

    Result<List<HouseListItemVO>> recommend(Integer count);

    Result<List<SelectedVO>> rentalHumanList();

}
