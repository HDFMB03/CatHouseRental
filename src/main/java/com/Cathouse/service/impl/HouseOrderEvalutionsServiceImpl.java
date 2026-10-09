package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.HouseOrderEvaluationsMapper;
import com.Cathouse.mapper.HouseOrderInfoMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderEvaluationsQueryDto;
import com.Cathouse.project.DataTransferObject.HouseOrderInfoQueryDto;
import com.Cathouse.project.entity.HouseOrderEvaluations;
import com.Cathouse.project.ValueObject.HouseOrderEvaluationsVO;
import com.Cathouse.service.HouseOrderEvaluationsService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HouseOrderEvalutionsServiceImpl extends ServiceImpl<HouseOrderEvaluationsMapper, HouseOrderEvaluations> implements HouseOrderEvaluationsService {

    @Resource
    private HouseOrderInfoMapper houseOrderInfoMapper;
    @Override
    public Result<List<HouseOrderEvaluationsVO>> list(HouseOrderEvaluationsQueryDto queryDto) {
        List<HouseOrderEvaluationsVO> houseOrderEvaluationsVOS = this.baseMapper.list(queryDto);
        Integer count = this.baseMapper.listCount(queryDto);
        return ApiResult.success(houseOrderEvaluationsVOS, count);
    }

    @Override
    public Result<String> saveEntity(HouseOrderEvaluations houseOrderEvaluations) {
        judge(houseOrderEvaluations);
        HouseOrderEvaluationsQueryDto queryDto = new HouseOrderEvaluationsQueryDto();
        queryDto.setUserId(LocalThreadHolder.getUserId());
        queryDto.setHouseOrderInfoId(houseOrderEvaluations.getHouseOrderInfoId());
        Integer count = this.baseMapper.listCount(queryDto);
        AssertUtils.isFalse(count > 0, "不能重复评论");
        houseOrderEvaluations.setCreateTime(LocalDateTime.now());
        houseOrderEvaluations.setUserId(LocalThreadHolder.getUserId());
        save(houseOrderEvaluations);
        return ApiResult.success();
    }
    private void judge(HouseOrderEvaluations houseOrderEvaluations) {
        AssertUtils.notNull(houseOrderEvaluations, "实体数据不能为空");
        AssertUtils.notNull(houseOrderEvaluations.getHouseOrderInfoId(), "预约订单ID不能为空");
        AssertUtils.hasText(houseOrderEvaluations.getText(), "评论内容不能为空");
        AssertUtils.notNull(houseOrderEvaluations.getScore(), "评分不能为空");
    }

    @Override
    public Result<List<HouseOrderEvaluationsVO>> houseList(Integer houseId) {
        AssertUtils.notNull(houseId,"房源ID不能为空");
        HouseOrderInfoQueryDto houseOrderInfoQueryDto = new HouseOrderInfoQueryDto();
        houseOrderInfoQueryDto.setHouseId(houseId);
        List<Integer> ids = houseOrderInfoMapper.getIdsByHouseId(houseId);
        if (ids.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        HouseOrderEvaluationsQueryDto houseOrderEvaluationsQueryDto = new HouseOrderEvaluationsQueryDto();
        houseOrderEvaluationsQueryDto.setHouseOrderInfoIds(ids);
        List<HouseOrderEvaluationsVO> houseOrderEvaluationsVOS = this.baseMapper.list(houseOrderEvaluationsQueryDto);
        return ApiResult.success(houseOrderEvaluationsVOS);
    }
}
