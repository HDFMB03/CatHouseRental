package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderEvaluationsQueryDto;
import com.Cathouse.project.entity.HouseOrderEvaluations;
import com.Cathouse.project.ValueObject.HouseOrderEvaluationsVO;
import com.Cathouse.service.HouseOrderEvaluationsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/house-order-evaluations")
public class HouseOrderEvaluationsController {

    @Resource
    private HouseOrderEvaluationsService houseOrderEvaluationsService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody HouseOrderEvaluations houseOrderEvaluations) {
        return houseOrderEvaluationsService.saveEntity(houseOrderEvaluations);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        houseOrderEvaluationsService.removeById(id);
        return ApiResult.success("预约看房评价删除成功");
    }

    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<List<HouseOrderEvaluationsVO>> listUser(@RequestBody HouseOrderEvaluationsQueryDto queryDto) {
        queryDto.setUserId(LocalThreadHolder.getUserId());
        return houseOrderEvaluationsService.list(queryDto);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<HouseOrderEvaluationsVO>> list(@RequestBody HouseOrderEvaluationsQueryDto queryDto) {
        return houseOrderEvaluationsService.list(queryDto);
    }

    @Pager
    @GetMapping(value = "/houseList/{houseId}")
    @ResponseBody
    public Result<List<HouseOrderEvaluationsVO>> houseList(@PathVariable Integer houseId) {
        return houseOrderEvaluationsService.houseList(houseId);
    }

}

