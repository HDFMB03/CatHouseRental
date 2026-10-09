package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.FlowIndexMapper;
import com.Cathouse.mapper.HouseMapper;
import com.Cathouse.mapper.HouseNewsMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.FlowIndexQueryDto;
import com.Cathouse.project.DataTransferObject.HouseNewsQueryDto;
import com.Cathouse.project.DataTransferObject.HouseQueryDto;
import com.Cathouse.project.EntityManager.FlowIndexEnum;
import com.Cathouse.project.entity.FlowIndex;
import com.Cathouse.project.ValueObject.HouseListItemVO;
import com.Cathouse.project.ValueObject.HouseNewsListVO;
import com.Cathouse.service.FlowIndexService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlowIndexServiceImpl extends ServiceImpl<FlowIndexMapper, FlowIndex> implements FlowIndexService {

    @Resource
    private HouseMapper houseMapper;
    @Resource
    private HouseNewsMapper houseNewsMapper;
    @Override
    public Result<List<FlowIndex>> list(FlowIndexQueryDto flowIndexQueryDto) {
        List<FlowIndex> flowIndexList = this.baseMapper.list(flowIndexQueryDto);
        Integer count = this.baseMapper.listCount(flowIndexQueryDto);
        return ApiResult.success(flowIndexList, count);
    }
    @Override
    public Result<String> saveEntity(FlowIndex flowIndex) {
        AssertUtils.notNull(flowIndex, "实体数据不能为空");
        AssertUtils.notNull(flowIndex.getType(), "请指定流量类型");
        AssertUtils.hasText(flowIndex.getContentType(), "请指定内容模块");
        AssertUtils.notNull(flowIndex.getContentId(), "请指定内容ID");
        if (Objects.equals(flowIndex.getType(), FlowIndexEnum.REMAIN.getType())) {
            AssertUtils.notNull(flowIndex.getTimes(), "停留时长不能为空");
        }
        flowIndex.setUserId(LocalThreadHolder.getUserId());
        flowIndex.setCreateTime(LocalDateTime.now());
        save(flowIndex);
        return ApiResult.success();
    }

    @Override
    public Result<String> viewOperation(FlowIndex flowIndex) {
        judge(flowIndex);
        FlowIndexQueryDto flowIndexQueryDto = createQueryDto(
                FlowIndexEnum.VIEW.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        Integer count = this.baseMapper.listCount(flowIndexQueryDto);
        AssertUtils.isFalse(count != 0, "已产生浏览行为");
        FlowIndex entity = createEntity(
                FlowIndexEnum.VIEW.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        save(entity);
        return ApiResult.success();
    }
    private FlowIndex createEntity(Integer type, Integer contentId, String contentType) {
        FlowIndex flowIndex = new FlowIndex();
        flowIndex.setType(type);
        flowIndex.setContentType(contentType);
        flowIndex.setContentId(contentId);
        flowIndex.setUserId(LocalThreadHolder.getUserId());
        flowIndex.setCreateTime(LocalDateTime.now());
        return flowIndex;
    }
    private void judge(FlowIndex flowIndex) {
        AssertUtils.notNull(flowIndex, "实体数据不能为空");
        AssertUtils.hasText(flowIndex.getContentType(), "请指定内容模块");
        AssertUtils.notNull(flowIndex.getContentId(), "请指定内容ID");
    }
    private FlowIndexQueryDto createQueryDto(Integer type, Integer contentId, String contentType) {
        FlowIndexQueryDto flowIndexQueryDto = new FlowIndexQueryDto();
        flowIndexQueryDto.setUserId(LocalThreadHolder.getUserId());
        flowIndexQueryDto.setType(type);
        flowIndexQueryDto.setContentId(contentId);
        flowIndexQueryDto.setContentType(contentType);
        return flowIndexQueryDto;
    }

    @Override
    public Result<String> saveOperation(FlowIndex flowIndex) {
        judge(flowIndex);
        FlowIndexQueryDto flowIndexQueryDto = createQueryDto(
                FlowIndexEnum.COLLECTION.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType());
        List<FlowIndex> flowIndexList = this.baseMapper.list(flowIndexQueryDto);
        if (!flowIndexList.isEmpty()) {
            removeById(flowIndexList.get(0).getId());
            return ApiResult.success("取消收藏成功");
        }
        FlowIndex entity = createEntity(
                FlowIndexEnum.COLLECTION.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        save(entity);
        return ApiResult.success("收藏成功");
    }

    @Override
    public Result<String> stayOperation(FlowIndex flowIndex) {
        judge(flowIndex);
        AssertUtils.notNull(flowIndex.getTimes(), "时长不为空");
        FlowIndex entity = createEntity(
                FlowIndexEnum.REMAIN.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        entity.setTimes(flowIndex.getTimes());
        save(entity);
        return ApiResult.success();
    }

    @Override
    public Result<List<HouseListItemVO>> saveListHouse(FlowIndexQueryDto flowIndexQueryDto) {
        flowIndexQueryDto.setContentType("HOUSE_INFO");
        List<FlowIndex> flowIndexList = getFlowList(flowIndexQueryDto);
        if (flowIndexList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        Integer count = this.baseMapper.listCount(flowIndexQueryDto);
        List<Integer> houseIds = flowIndexList.stream()
                .map(FlowIndex::getContentId)
                .collect(Collectors.toList());
        HouseQueryDto houseQueryDto = new HouseQueryDto();
        houseQueryDto.setIds(houseIds);
        List<HouseListItemVO> houseListItemVOS = houseMapper.list(houseQueryDto);
        return ApiResult.success(houseListItemVOS, count);
    }

    @Override
    public Result<List<HouseNewsListVO>> saveListHouseNews(FlowIndexQueryDto flowIndexQueryDto) {
        flowIndexQueryDto.setContentType("HOUSE_NEWS");
        List<FlowIndex> flowIndexList = getFlowList(flowIndexQueryDto);
        if (flowIndexList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        Integer count = this.baseMapper.listCount(flowIndexQueryDto);
        List<Integer> houseNewsId = flowIndexList.stream()
                .map(FlowIndex::getContentId)
                .collect(Collectors.toList());
        HouseNewsQueryDto houseNewsQueryDto = new HouseNewsQueryDto();
        houseNewsQueryDto.setIds(houseNewsId);
        List<HouseNewsListVO> houseNewsListVOS = houseNewsMapper.list(houseNewsQueryDto);
        return ApiResult.success(houseNewsListVOS, count);
    }

    private List<FlowIndex> getFlowList(FlowIndexQueryDto flowIndexQueryDto) {
        flowIndexQueryDto.setType(FlowIndexEnum.COLLECTION.getType());
        flowIndexQueryDto.setUserId(LocalThreadHolder.getUserId());
        return this.baseMapper.list(flowIndexQueryDto);
    }

}


