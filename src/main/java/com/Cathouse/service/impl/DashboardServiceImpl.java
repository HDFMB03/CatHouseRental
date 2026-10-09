package com.Cathouse.service.impl;

import com.Cathouse.mapper.HouseMapper;
import com.Cathouse.mapper.HouseNewsMapper;
import com.Cathouse.mapper.HouseOrderInfoMapper;
import com.Cathouse.mapper.LandlordMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.*;
import com.Cathouse.project.ValueObject.ChartVO;
import com.Cathouse.project.ValueObject.HouseListItemVO;
import com.Cathouse.service.DashboardService;
import com.Cathouse.utils.AssertUtils;
import com.Cathouse.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private LandlordMapper landlordMapper;
    @Resource
    private HouseMapper houseMapper;
    @Resource
    private HouseNewsMapper houseNewsMapper;
    @Resource
    private HouseOrderInfoMapper houseOrderInfoMapper;
    @Override
    public Result<List<ChartVO>> mainStatic() {
        List<ChartVO> chartVOList = new ArrayList<>();
        Integer landlordNumber = landlordMapper.listCount(new LandlordQueryDto());
        ChartVO landlordChartVO = new ChartVO("房东人数（个）", landlordNumber);
        chartVOList.add(landlordChartVO);

        Integer houseNumber = houseMapper.listCount(new HouseQueryDto());
        ChartVO houseChartVO = new ChartVO("房源数目（户）", houseNumber);
        chartVOList.add(houseChartVO);

        Integer houseNewsNumber = houseNewsMapper.listCount(new HouseNewsQueryDto());
        ChartVO houseNewsChartVO = new ChartVO("房屋资讯（篇）", houseNewsNumber);
        chartVOList.add(houseNewsChartVO);

        Integer houseOrderInfoNumber = houseOrderInfoMapper.listCount(new HouseOrderInfoQueryDto());
        ChartVO houseOrderInfoChartVO = new ChartVO("预约看房订单（条）", houseOrderInfoNumber);
        chartVOList.add(houseOrderInfoChartVO);
        return ApiResult.success(chartVOList);
    }

    @Override
    public Result<List<ChartVO>> houseLineChart(Integer days) {
        AssertUtils.notNull(days, "日期不能为空");
        QueryDto queryDto = DateUtil.startAndEndTime(days);
        HouseQueryDto houseQueryDto = new HouseQueryDto();
        houseQueryDto.setStartTime(queryDto.getStartTime());
        houseQueryDto.setEndTime(queryDto.getEndTime());
        List<HouseListItemVO> houseListItemVOS = houseMapper.listCreate(houseQueryDto);

        if (houseListItemVOS.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        List<LocalDateTime> dateTimeList = houseListItemVOS.stream()
                .map(HouseListItemVO::getCreateTime)
                .collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(days, dateTimeList);
        return ApiResult.success(chartVOS);
    }

    @Override
    public Result<List<ChartVO>> cityHouseRange(Integer limit) {
        AssertUtils.notNull(limit, "查询条数不能为空");
        List<ChartVO> chartVOS = houseMapper.cityHouseRange(limit);
        return ApiResult.success(chartVOS);
    }
}
