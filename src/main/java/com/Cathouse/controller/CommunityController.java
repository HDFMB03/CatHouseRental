package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.CommunityQueryDto;
import com.Cathouse.project.entity.Community;
import com.Cathouse.project.ValueObject.CommunityVO;
import com.Cathouse.service.CommunityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Resource
    private CommunityService communityService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody Community community) {
        return communityService.saveEntity(community);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        communityService.removeById(id);
        return ApiResult.success("小区删除成功");
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody Community community) {
        return communityService.update(community);
    }

    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public Result<CommunityVO> getById(@PathVariable Integer id) {
        return communityService.selectById(id);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<CommunityVO>> list(@RequestBody CommunityQueryDto communityQueryDto) {
        return communityService.list(communityQueryDto);
    }

}

