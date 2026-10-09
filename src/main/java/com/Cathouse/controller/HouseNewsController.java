package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseNewsQueryDto;
import com.Cathouse.project.entity.HouseNews;
import com.Cathouse.project.ValueObject.HouseNewsListVO;
import com.Cathouse.service.HouseNewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/house-news")
public class HouseNewsController {

    @Resource
    private HouseNewsService houseNewsService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody HouseNews houseNews) {
        return houseNewsService.saveEntity(houseNews);
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody HouseNews houseNews) {
        return houseNewsService.updateEntity(houseNews);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        houseNewsService.removeById(id);
        return ApiResult.success("房屋资讯删除成功");
    }

    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public Result<HouseNews> getById(@PathVariable Integer id) {
        return houseNewsService.selectById(id);
    }

    @GetMapping(value = "/views/{id}")
    @ResponseBody
    public Result<Integer> getViewsById(@PathVariable Integer id) {
        return houseNewsService.getViewsById(id);
    }

    @PostMapping(value = "/increaseView/{id}")
    @ResponseBody
    public Result<String> increaseView(@PathVariable Integer id) {
        return houseNewsService.increaseViewById(id);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<HouseNewsListVO>> list(@RequestBody HouseNewsQueryDto houseNewsQueryDto) {
        return houseNewsService.list(houseNewsQueryDto);
    }

    @GetMapping(value = "/recommend/{count}")
    @ResponseBody
    public Result<List<HouseNewsListVO>> recommend(@PathVariable Integer count) {
        return houseNewsService.recommend(count);
    }

}

