package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.NoticeQueryDto;
import com.Cathouse.project.entity.Notice;
import com.Cathouse.project.ValueObject.NoticeListItemVO;
import com.Cathouse.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody Notice notice) {
        return noticeService.saveEntity(notice);
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<String> update(@RequestBody Notice notice) {
        return noticeService.updateEntity(notice);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return ApiResult.success("公告删除成功");
    }

    @GetMapping(value = "/views/{id}")
    @ResponseBody
    public Result<Integer> getViewsById(@PathVariable Integer id) {
        return noticeService.getViewsById(id);
    }

    @PostMapping(value = "/increaseView/{id}")
    @ResponseBody
    public Result<String> increaseView(@PathVariable Integer id) {
        return noticeService.increaseViewById(id);
    }


    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public Result<Notice> getById(@PathVariable Integer id) {
        return noticeService.selectById(id);
    }

    @Pager
    @PostMapping(value = "/list")
    @ResponseBody
    public Result<List<NoticeListItemVO>> list(@RequestBody NoticeQueryDto noticeQueryDto) {
        return noticeService.list(noticeQueryDto);
    }

}

