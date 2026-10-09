package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.HouseMapper;
import com.Cathouse.mapper.HouseOrderInfoMapper;
import com.Cathouse.mapper.HouseOrderStatusMapper;
import com.Cathouse.mapper.LandlordMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseOrderInfoQueryDto;
import com.Cathouse.project.DataTransferObject.LandlordQueryDto;
import com.Cathouse.project.EntityManager.DateTimeSplitEnum;
import com.Cathouse.project.EntityManager.HouseOrderStatusEnum;
import com.Cathouse.project.entity.HouseOrderInfo;
import com.Cathouse.project.entity.HouseOrderStatus;
import com.Cathouse.project.ValueObject.HouseOrderInfoVO;
import com.Cathouse.project.ValueObject.LandlordVO;
import com.Cathouse.service.HouseOrderInfoService;
import com.Cathouse.utils.AssertUtils;
import com.Cathouse.utils.DateFormatUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class HouseOrderInfoServiceImpl extends ServiceImpl<HouseOrderInfoMapper, HouseOrderInfo> implements HouseOrderInfoService {

    @Resource
    private HouseOrderStatusMapper houseOrderStatusMapper;
    @Resource
    private LandlordMapper landlordMapper;
    @Resource
    private HouseMapper houseMapper;
    @Override
    public Result<List<HouseOrderInfoVO>> list(HouseOrderInfoQueryDto houseOrderInfoQueryDto) {
        List<HouseOrderInfoVO> houseNewsListVOList = this.baseMapper.list(houseOrderInfoQueryDto);
        Integer count = this.baseMapper.listCount(houseOrderInfoQueryDto);
        return ApiResult.success(houseNewsListVOList, count);
    }
    @Override
    public Result<String> saveEntity(HouseOrderInfo houseOrderInfo) {
        judge(houseOrderInfo);
        validHouseAuth(houseOrderInfo);
        validDateSplit(houseOrderInfo);
        validStatus(houseOrderInfo);
        defaultCreate(houseOrderInfo);
        save(houseOrderInfo);
        return ApiResult.success();
    }

    private void validHouseAuth(HouseOrderInfo houseOrderInfo) {
        Integer houseId = houseOrderInfo.getHouseId();
        LandlordQueryDto landlordQueryDto = new LandlordQueryDto();
        landlordQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = landlordMapper.list(landlordQueryDto);
        if (landlordVOS.isEmpty()) {
            return;
        }
        LandlordVO landlordVO = landlordVOS.get(0);
        Integer landlordId = landlordVO.getId();
        List<Integer> houseIds = houseMapper.selectIdsByLandlordId(landlordId);
        if (houseIds.isEmpty()) {
            return;
        }
        List<Integer> houseIdsExit = houseIds.stream().filter(id -> Objects.equals(id, houseId)).
                collect(Collectors.toList());
        AssertUtils.isTrue(houseIdsExit.isEmpty(), " (￣△￣;)，请勿预约自己名下房源信息");
    }

    private void defaultCreate(HouseOrderInfo houseOrderInfo) {
        houseOrderInfo.setCreateTime(LocalDateTime.now());
        houseOrderInfo.setUserId(LocalThreadHolder.getUserId());
        houseOrderInfo.setOrderStatus(HouseOrderStatusEnum.STATUS_1.getType());
    }

    private void validDateSplit(HouseOrderInfo houseOrderInfo) {
        LocalDate localDate = DateFormatUtil.parseChineseDate(houseOrderInfo.getOrderDate());
        boolean timeSlotValid = DateTimeSplitEnum.isTimeSlotValid(houseOrderInfo.getOrderTimeSplit(), localDate);
        AssertUtils.isTrue(timeSlotValid, "当前时间段不能预约");
    }

    private void validStatus(HouseOrderInfo houseOrderInfo) {
        HouseOrderInfoQueryDto houseOrderInfoQueryDto = new HouseOrderInfoQueryDto();
        houseOrderInfoQueryDto.setHouseId(houseOrderInfo.getHouseId());
        houseOrderInfoQueryDto.setOrderDate(houseOrderInfo.getOrderDate());
        houseOrderInfoQueryDto.setOrderTimeSplit(houseOrderInfo.getOrderTimeSplit());
        List<HouseOrderInfoVO> houseOrderInfoVOS = this.baseMapper.list(houseOrderInfoQueryDto);
        for (HouseOrderInfoVO houseOrderInfoVO : houseOrderInfoVOS) {
            AssertUtils.isFalse(
                    Objects.equals(HouseOrderStatusEnum.STATUS_1.getType(), houseOrderInfoVO.getOrderStatus()) ||
                            Objects.equals(houseOrderInfoVO.getOrderStatus(), HouseOrderStatusEnum.STATUS_2.getType()),
                    "当前时间段已被预约，请选择其他时段喵");
        }
    }

    @Override
    public Result<String> updateEntity(HouseOrderInfo houseOrderInfo) {
        judge(houseOrderInfo);
        statusFlow(houseOrderInfo);
        updateById(houseOrderInfo);
        return ApiResult.success();
    }

    private void statusFlow(HouseOrderInfo houseOrderInfo) {
        HouseOrderInfo orderInfo = getById(houseOrderInfo.getId());
        AssertUtils.notNull(orderInfo, "查询异常");
        AssertUtils.notNull(orderInfo.getOrderStatus(), "原始状态缺失");
        Integer originStatus = orderInfo.getOrderStatus();
        if (!Objects.equals(originStatus, houseOrderInfo.getOrderStatus())) {
            HouseOrderStatus houseOrderStatus = new HouseOrderStatus();
            houseOrderStatus.setOriginStatus(originStatus);
            houseOrderStatus.setNewId(houseOrderInfo.getOrderStatus());
            houseOrderStatus.setCreateTime(LocalDateTime.now());
            houseOrderStatus.setHouseOrderInfoId(houseOrderInfo.getId());
            houseOrderStatusMapper.insert(houseOrderStatus);
        }
    }
    private void judge(HouseOrderInfo houseOrderInfo) {
        AssertUtils.notNull(houseOrderInfo, "实体数据不能为空");
        AssertUtils.notNull(houseOrderInfo.getHouseId(), "房源ID不能为空");
        AssertUtils.hasText(houseOrderInfo.getOrderDate(), "必须选择日期");
        AssertUtils.hasText(houseOrderInfo.getOrderTimeSplit(), "时间段不能为空");
    }

    @Override
    public Result<List<HouseOrderInfoVO>> listLandlord(HouseOrderInfoQueryDto houseOrderInfoQueryDto) {
        LandlordVO landlord = getLandlord();
        AssertUtils.isTrue(landlord.getIsAudit(), "房东认证信息待审核，请稍后再试");
        List<Integer> houseIds = houseMapper.selectIdsByLandlordId(landlord.getId());
        if (houseIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        houseOrderInfoQueryDto.setHouseIds(houseIds);
        List<HouseOrderInfoVO> houseOrderInfoVOS = this.baseMapper.list(houseOrderInfoQueryDto);
        Integer count = this.baseMapper.listCount(houseOrderInfoQueryDto);
        return ApiResult.success(houseOrderInfoVOS, count);
    }

    private LandlordVO getLandlord() {
        LandlordQueryDto landlordQueryDto = new LandlordQueryDto();
        landlordQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = landlordMapper.list(landlordQueryDto);
        AssertUtils.isFalse(landlordVOS.isEmpty(), "房东信息异常，非法操作");
        return landlordVOS.get(0);
    }
}
