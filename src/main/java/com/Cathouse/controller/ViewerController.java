package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseNewsQueryDto;
import com.Cathouse.project.DataTransferObject.HouseQueryDto;
import com.Cathouse.project.entity.HouseNews;
import com.Cathouse.project.ValueObject.HouseListItemVO;
import com.Cathouse.project.ValueObject.HouseNewsListVO;
import com.Cathouse.project.ValueObject.HouseVO;
import com.Cathouse.project.ValueObject.SelectedVO;
import com.Cathouse.service.HouseNewsService;
import com.Cathouse.service.HouseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/viewer")
public class ViewerController {

    @Resource
    private HouseService houseService;
    @Resource
    private HouseNewsService houseNewsService;

    @Pager
    @PostMapping(value = "/listHouse")
    @ResponseBody
    public Result<List<HouseListItemVO>> list(@RequestBody HouseQueryDto houseQueryDto) {
        return houseService.list(houseQueryDto);
    }

    @GetMapping(value = "/houseSizedList")
    @ResponseBody
    public Result<List<SelectedVO>> houseSizedList() {
        return houseService.houseSizedList();
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

    @GetMapping(value = "/getHouseById/{id}")
    @ResponseBody
    public Result<HouseVO> selectById(@PathVariable Integer id) {
        return houseService.selectById(id);
    }

    @Pager
    @PostMapping(value = "/listHouseNews")
    @ResponseBody
    public Result<List<HouseNewsListVO>> list(@RequestBody HouseNewsQueryDto houseNewsQueryDto) {
        return houseNewsService.list(houseNewsQueryDto);
    }

    @GetMapping(value = "/getNewsById/{id}")
    @ResponseBody
    public Result<HouseNews> getById(@PathVariable Integer id) {
        return houseNewsService.selectById(id);
    }

}

