package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.NoticeQueryDto;
import com.Cathouse.project.entity.Notice;
import com.Cathouse.project.ValueObject.NoticeListItemVO;

import java.util.List;

public interface NoticeService extends IService<Notice> {

    Result<List<NoticeListItemVO>> list(NoticeQueryDto noticeQueryDto);

    Result<String> saveEntity(Notice notice);

    Result<Notice> selectById(Integer id);

    Result<String> updateEntity(Notice notice);

    Result<Integer> getViewsById(Integer id);

    Result<String> increaseViewById(Integer id);

}
