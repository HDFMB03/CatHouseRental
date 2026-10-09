package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.mapper.NoticeMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.NoticeQueryDto;
import com.Cathouse.project.entity.Notice;
import com.Cathouse.project.ValueObject.NoticeListItemVO;
import com.Cathouse.service.NoticeService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public Result<List<NoticeListItemVO>> list(NoticeQueryDto noticeQueryDto) {
        List<NoticeListItemVO> noticeListItemVOS = this.baseMapper.list(noticeQueryDto);
        Integer count = this.baseMapper.listCount(noticeQueryDto);
        return ApiResult.success(noticeListItemVOS, count);
    }

    @Override
    public Result<String> saveEntity(Notice notice) {
        judge(notice);
        notice.setCreateTime(LocalDateTime.now());
        save(notice);
        return ApiResult.success();
    }

    @Override
    public Result<Notice> selectById(Integer id) {
        AssertUtils.notNull(id, "ID不能为空");
        Notice notice = getById(id);
        return ApiResult.success(notice);
    }

    @Override
    public Result<String> updateEntity(Notice notice) {
        judge(notice);
        updateById(notice);
        return ApiResult.success();
    }

    @Override
    public Result<Integer> getViewsById(Integer id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            return ApiResult.fail("未找到对应的公告");
        }
        Integer viewNumber = notice.getViewNumber();
        if (viewNumber == null) {
            viewNumber = 0;
        }
        return ApiResult.success(viewNumber);
    }

    @Override
    public Result<String> increaseViewById(Integer id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            return ApiResult.fail("未找到对应的公告");
        }
        Integer currentViewNumber = notice.getViewNumber();
        if (currentViewNumber == null) {
            currentViewNumber = 0;
        }
        notice.setViewNumber(currentViewNumber + 1);
        boolean success = this.updateById(notice);
        if (success) {
            return ApiResult.success("浏览量增加成功");
        } else {
            return ApiResult.fail("浏览量增加失败");
        }
    }

    private void judge(Notice notice) {
        AssertUtils.hasText(notice.getTitle(), "标题不能为空");
        AssertUtils.isTrue(notice.getTitle().length() < 30, "标题最多30个字");

    }


}
