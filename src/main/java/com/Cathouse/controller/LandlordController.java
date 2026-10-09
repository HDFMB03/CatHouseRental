package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.LandlordQueryDto;
import com.Cathouse.project.entity.Landlord;
import com.Cathouse.project.ValueObject.LandlordVO;
import com.Cathouse.service.LandlordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/landlord")
public class LandlordController {

    @Resource
    private LandlordService landlordService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody Landlord landlord) {
        return landlordService.saveEntity(landlord);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        landlordService.removeById(id);
        return ApiResult.success("房东删除成功");
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody Landlord landlord) {
        return landlordService.update(landlord);
    }

    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<LandlordVO> listUser(@RequestBody LandlordQueryDto landlordQueryDto) {
        return landlordService.listUser(landlordQueryDto);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<LandlordVO>> list(@RequestBody LandlordQueryDto landlordQueryDto) {
        return landlordService.list(landlordQueryDto);
    }

}

