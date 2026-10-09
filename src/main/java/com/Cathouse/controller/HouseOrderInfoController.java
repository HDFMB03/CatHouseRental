package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderInfoQueryDto;
import com.Cathouse.project.EntityManager.DateTimeSplitEnum;
import com.Cathouse.project.entity.HouseOrderInfo;
import com.Cathouse.project.ValueObject.HouseOrderInfoVO;
import com.Cathouse.project.ValueObject.SelectedVO;
import com.Cathouse.service.HouseOrderInfoService;
import com.Cathouse.utils.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/house-order-info")
public class HouseOrderInfoController {

    @Resource
    private HouseOrderInfoService houseOrderInfoService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody HouseOrderInfo houseOrderInfo) {
        return houseOrderInfoService.saveEntity(houseOrderInfo);
    }

    @GetMapping(value = "/split")
    @ResponseBody
    public Result<List<SelectedVO>> split() {
        DateTimeSplitEnum[] dateTimeSplitEnums = DateTimeSplitEnum.values();
        List<SelectedVO> selectedVOS = new ArrayList<>();
        for (DateTimeSplitEnum dateTimeSplitEnum : dateTimeSplitEnums) {
            SelectedVO selectedVO = new SelectedVO(dateTimeSplitEnum.getType(), dateTimeSplitEnum.getDetail());
            selectedVOS.add(selectedVO);
        }
        return ApiResult.success(selectedVOS);
    }

    @GetMapping(value = "/{days}")
    @ResponseBody
    public Result<List<String>> generateFutureDates(@PathVariable Integer days) {
        return ApiResult.success(DateUtil.generateFutureDates(days));
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody HouseOrderInfo houseOrderInfo) {
        return houseOrderInfoService.updateEntity(houseOrderInfo);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        houseOrderInfoService.removeById(id);
        return ApiResult.success("预约看房删除成功");
    }

    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<List<HouseOrderInfoVO>> listUser(@RequestBody HouseOrderInfoQueryDto houseOrderInfoQueryDto) {
        houseOrderInfoQueryDto.setUserId(LocalThreadHolder.getUserId());
        return houseOrderInfoService.list(houseOrderInfoQueryDto);
    }

    @Pager
    @PostMapping(value = "/listLandlord")
    @ResponseBody
    public Result<List<HouseOrderInfoVO>> listLandlord(@RequestBody HouseOrderInfoQueryDto houseOrderInfoQueryDto) {
        return houseOrderInfoService.listLandlord(houseOrderInfoQueryDto);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<HouseOrderInfoVO>> list(@RequestBody HouseOrderInfoQueryDto houseOrderInfoQueryDto) {
        return houseOrderInfoService.list(houseOrderInfoQueryDto);
    }

}

