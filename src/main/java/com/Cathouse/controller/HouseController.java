package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.mapper.HouseMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseQueryDto;
import com.Cathouse.project.entity.House;
import com.Cathouse.project.ValueObject.*;
import com.Cathouse.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Resource
    private HouseService houseService;
    @Autowired
    private HouseMapper houseMapper;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody House house) {
        return houseService.saveEntity(house);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        houseService.removeById(id);
        return ApiResult.success("房屋删除成功");
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody House house) {
        return houseService.update(house);
    }

    @GetMapping(value = "/houseTypeList")
    @ResponseBody
    public Result<List<SelectedVO>> houseTypeList() {
        return houseService.houseTypeList();
    }

    @GetMapping(value = "/houseDirectionList")
    @ResponseBody
    public Result<List<SelectedVO>> houseDirectionList() {
        return houseService.houseDirectionList();
    }

    @GetMapping(value = "/houseSizedList")
    @ResponseBody
    public Result<List<SelectedVO>> houseSizedList() {
        return houseService.houseSizedList();
    }

    @GetMapping(value = "/houseDepositMethodList")
    @ResponseBody
    public Result<List<SelectedVO>> houseDepositMethodList() {
        return houseService.houseDepositMethodList();
    }

    @GetMapping(value = "/houseSubwayList")
    @ResponseBody
    public Result<List<SelectedVO>> houseSubwayList() {
        return houseService.houseSubwayList();
    }

    @GetMapping(value = "/rentalHumanList")
    @ResponseBody
    public Result<List<SelectedVO>> rentalHumanList() {
        return houseService.rentalHumanList();
    }

    @GetMapping(value = "/houseFitmentStatusList")
    @ResponseBody
    public Result<List<SelectedVO>> houseFitmentStatusList() {
        return houseService.houseFitmentStatusList();
    }

    @GetMapping(value = "/houseRentalTypeList")
    @ResponseBody
    public Result<List<SelectedVO>> houseRentalTypeList() {
        return houseService.houseRentalTypeList();
    }

    @GetMapping(value = "/houseLivingFacilityList")
    @ResponseBody
    public Result<List<LivingFacilityVO>> houseLivingFacilityList() {
        return houseService.houseLivingFacilityList();
    }

    @GetMapping(value = "/houseSizeNumber")
    @ResponseBody
    public Result<List<SelectedVO>> houseSizeNumber() {
        return houseService.houseSizeNumber();
    }

    @GetMapping(value = "/houseRentRange")
    @ResponseBody
    public Result<List<SelectedVO>> houseRentRange() {
        return houseService.houseRentRange();
    }

    @Pager
    @PostMapping(value = "/landlordHouseList")
    @ResponseBody
    public Result<List<HouseListItemVO>> landlordHouseList(@RequestBody HouseQueryDto houseQueryDto) {
        return houseService.landlordHouseList(houseQueryDto);
    }

    @Pager
    @PostMapping(value = "/listFlowIndex")
    @ResponseBody
    public Result<List<HouseFlowIndexVO>> listFlowIndex(@RequestBody HouseQueryDto houseQueryDto) {
        return houseService.listFlowIndex(houseQueryDto);
    }

    @PutMapping(value = "/houseStatusDeal/{id}")
    @ResponseBody
    public Result<String> houseStatusDeal(@PathVariable Integer id) {
        return houseService.houseStatusDeal(id);
    }

    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public Result<HouseVO> selectById(@PathVariable Integer id) {
        return houseService.selectById(id);
    }

    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<List<HouseListItemVO>> listUser(@RequestBody HouseQueryDto houseQueryDto) {
        return houseService.listUser(houseQueryDto);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<HouseListItemVO>> list(@RequestBody HouseQueryDto houseQueryDto) {
        return houseService.list(houseQueryDto);
    }

    @Pager
    @PostMapping(value = "/listChart")
    @ResponseBody
    public Result<List<ChartVO>> listChart(@RequestBody HouseQueryDto houseQueryDto) {
        return houseService.listChart(houseQueryDto);
    }

    @GetMapping(value = "/recommend/{count}")
    @ResponseBody
    public Result<List<HouseListItemVO>> recommend(@PathVariable Integer count) {
        return houseService.recommend(count);
    }

}

