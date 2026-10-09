package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderStatusQueryDto;
import com.Cathouse.project.ValueObject.HouseOrderStatusVO;
import com.Cathouse.service.HouseOrderStatusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/house-order-status")
public class HouseOrderStatusController {

    @Resource
    private HouseOrderStatusService houseOrderStatusService;

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<HouseOrderStatusVO>> list(@RequestBody HouseOrderStatusQueryDto queryDto) {
        return houseOrderStatusService.list(queryDto);
    }

}

