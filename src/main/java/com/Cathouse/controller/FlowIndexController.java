package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.FlowIndexQueryDto;
import com.Cathouse.project.entity.FlowIndex;
import com.Cathouse.project.ValueObject.HouseListItemVO;
import com.Cathouse.project.ValueObject.HouseNewsListVO;
import com.Cathouse.service.FlowIndexService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/flow-index")
public class FlowIndexController {

    @Resource
    private FlowIndexService flowIndexService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody FlowIndex flowIndex) {
        return flowIndexService.saveEntity(flowIndex);
    }

    @PostMapping(value = "/viewOperation")
    @ResponseBody
    public Result<String> viewOperation(@RequestBody FlowIndex flowIndex) {
        return flowIndexService.viewOperation(flowIndex);
    }

    @PostMapping(value = "/stayOperation")
    @ResponseBody
    public Result<String> stayOperation(@RequestBody FlowIndex flowIndex) {
        return flowIndexService.stayOperation(flowIndex);
    }

    @PostMapping(value = "/saveOperation")
    @ResponseBody
    public Result<String> saveOperation(@RequestBody FlowIndex flowIndex) {
        return flowIndexService.saveOperation(flowIndex);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        flowIndexService.removeById(id);
        return ApiResult.success("流量指标删除成功");
    }

    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<List<FlowIndex>> listUser(@RequestBody FlowIndexQueryDto flowIndexQueryDto) {
        flowIndexQueryDto.setUserId(LocalThreadHolder.getUserId());
        return flowIndexService.list(flowIndexQueryDto);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<FlowIndex>> list(@RequestBody FlowIndexQueryDto flowIndexQueryDto) {
        return flowIndexService.list(flowIndexQueryDto);
    }

    @Pager
    @PostMapping(value = "/saveListHouse")
    @ResponseBody
    public Result<List<HouseListItemVO>> saveListHouse(@RequestBody FlowIndexQueryDto flowIndexQueryDto) {
        return flowIndexService.saveListHouse(flowIndexQueryDto);
    }

    @Pager
    @PostMapping(value = "/saveListHouseNews")
    @ResponseBody
    public Result<List<HouseNewsListVO>> saveListHouseNews(@RequestBody FlowIndexQueryDto flowIndexQueryDto) {
        return flowIndexService.saveListHouseNews(flowIndexQueryDto);
    }

}

