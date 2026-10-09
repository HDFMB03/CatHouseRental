package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.AreaQueryDto;
import com.Cathouse.project.entity.Area;
import com.Cathouse.service.AreaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody Area area) {
        return areaService.saveEntity(area);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        areaService.removeById(id);
        return ApiResult.success("地区删除成功");
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody Area area) {
        return areaService.update(area);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<Area>> list(@RequestBody AreaQueryDto areaQueryDto) {
        return areaService.list(areaQueryDto);
    }

}

