package com.Cathouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.Cathouse.project.DataTransferObject.NoticeQueryDto;
import com.Cathouse.project.entity.Notice;
import com.Cathouse.project.ValueObject.NoticeListItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    List<NoticeListItemVO> list(NoticeQueryDto noticeQueryDto);

    Integer listCount(NoticeQueryDto noticeQueryDto);

}
