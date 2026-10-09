package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.UserAreaQueryDto;
import com.Cathouse.project.entity.UserArea;
import com.Cathouse.project.ValueObject.UserAreaVO;
import com.Cathouse.service.UserAreaService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user-area")
public class UserAreaController {

    @Resource
    private UserAreaService userAreaService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody UserArea userArea) {
        return userAreaService.saveEntity(userArea);
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody UserArea userArea) {
        return userAreaService.update(userArea);
    }

    @Pager
    @PostMapping(value = "/listUser")
    @ResponseBody
    public Result<List<UserAreaVO>> listUser(@RequestBody UserAreaQueryDto userAreaQueryDto) {
        userAreaQueryDto.setUserId(LocalThreadHolder.getUserId());
        return userAreaService.listUser(userAreaQueryDto);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<UserArea>> list(@RequestBody UserAreaQueryDto userAreaQueryDto) {
        return userAreaService.list(userAreaQueryDto);
    }

}

