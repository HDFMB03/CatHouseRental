package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.mapper.HouseOrderStatusMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderStatusQueryDto;
import com.Cathouse.project.EntityManager.HouseOrderStatusEnum;
import com.Cathouse.project.entity.HouseOrderStatus;
import com.Cathouse.project.ValueObject.HouseOrderStatusVO;
import com.Cathouse.service.HouseOrderStatusService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HouseOrderStatusServiceImpl extends ServiceImpl<HouseOrderStatusMapper, HouseOrderStatus> implements HouseOrderStatusService {

    @Override
    public Result<List<HouseOrderStatusVO>> list(HouseOrderStatusQueryDto queryDto) {
        List<HouseOrderStatusVO> houseOrderStatusVOS = this.baseMapper.list(queryDto);
        Integer count = this.baseMapper.listCount(queryDto);
        statusChange(houseOrderStatusVOS);
        return ApiResult.success(houseOrderStatusVOS, count);
    }

    private void statusChange(List<HouseOrderStatusVO> houseOrderStatusVOS) {
        if (houseOrderStatusVOS != null && !houseOrderStatusVOS.isEmpty()) {
            for (HouseOrderStatusVO houseOrderStatusVO : houseOrderStatusVOS) {
                String originText = HouseOrderStatusEnum.getDetail(houseOrderStatusVO.getOriginStatus());
                houseOrderStatusVO.setOldStatusText(originText);
                String newText = HouseOrderStatusEnum.getDetail(houseOrderStatusVO.getNewId());
                houseOrderStatusVO.setNewStatusText(newText);
            }
        }
    }

    @Override
    public Result<String> saveEntity(HouseOrderStatus houseOrderStatus) {
        judge(houseOrderStatus);
        houseOrderStatus.setCreateTime(LocalDateTime.now());
        save(houseOrderStatus);
        return ApiResult.success();
    }

    private void judge(HouseOrderStatus houseOrderStatus) {
        AssertUtils.notNull(houseOrderStatus, "实体数据不能为空");
        AssertUtils.notNull(houseOrderStatus.getHouseOrderInfoId(), "预约订单ID不能为空");
        AssertUtils.notNull(houseOrderStatus.getOriginStatus(), "旧状态不能为空");
        AssertUtils.notNull(houseOrderStatus.getNewId(), "新状态不能为空");
    }


}
