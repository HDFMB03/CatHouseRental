package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.FlowIndexMapper;
import com.Cathouse.mapper.HouseNewsMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.FlowIndexQueryDto;
import com.Cathouse.project.DataTransferObject.HouseNewsQueryDto;
import com.Cathouse.project.entity.HouseNews;
import com.Cathouse.project.ValueObject.HouseNewsListVO;
import com.Cathouse.project.ValueObject.ScoreVO;
import com.Cathouse.service.HouseNewsService;
import com.Cathouse.utils.AssertUtils;
import com.Cathouse.utils.UserBasedCFUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HouseNewsServiceImpl extends ServiceImpl<HouseNewsMapper, HouseNews> implements HouseNewsService {

    private final FlowIndexMapper flowIndexMapper;

    public HouseNewsServiceImpl(FlowIndexMapper flowIndexMapper) {
        this.flowIndexMapper = flowIndexMapper;
    }
    @Override
    public Result<List<HouseNewsListVO>> list(HouseNewsQueryDto houseNewsQueryDto) {
        List<HouseNewsListVO> houseNewsListVOList = this.baseMapper.list(houseNewsQueryDto);
        Integer count = this.baseMapper.listCount(houseNewsQueryDto);
        return ApiResult.success(houseNewsListVOList, count);
    }
    @Override
    public Result<String> saveEntity(HouseNews houseNews) {
        judge(houseNews);
        houseNews.setCreateTime(LocalDateTime.now());
        save(houseNews);
        return ApiResult.success();
    }
    @Override
    public Result<HouseNews> selectById(Integer id) {
        AssertUtils.notNull(id, "ID不能为空");
        HouseNews houseNews = getById(id);
        return ApiResult.success(houseNews);
    }


    @Override
    public Result<String> updateEntity(HouseNews houseNews) {
        judge(houseNews);
        updateById(houseNews);
        return ApiResult.success();
    }
    private void judge(HouseNews houseNews) {
        AssertUtils.notNull(houseNews, "信息不能为空");
        AssertUtils.hasText(houseNews.getTitle(), "标题不能为空");
        AssertUtils.hasText(houseNews.getCover(), "封面不能为空");
        AssertUtils.hasText(houseNews.getSummary(), "摘要不能为空");
        AssertUtils.isTrue(houseNews.getTitle().length() < 50, "标题最多50个字");
        AssertUtils.isTrue(houseNews.getSummary().length() < 200, "摘要最多200个字");
    }
    @Override
    public Result<List<HouseNewsListVO>> recommend(Integer count) {
        AssertUtils.notNull(count, "推荐条数不能为空");
        FlowIndexQueryDto flowIndexQueryDto = new FlowIndexQueryDto();
        flowIndexQueryDto.setContentType("HOUSE_NEWS");
        List<Integer> houseNewsIds = this.baseMapper.getIds();
        if (houseNewsIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        List<ScoreVO> scores = flowIndexMapper.getScores(flowIndexQueryDto);
        if (scores.isEmpty()) {
            List<HouseNewsListVO> houseNewsListVOList = queryHouseNews(houseNewsIds.size() > count ? houseNewsIds.subList(0, count) : houseNewsIds);
            return ApiResult.success(houseNewsListVOList);
        }
        List<UserBasedCFUtil.Score> scoreList = scores.stream().map(score -> new UserBasedCFUtil.Score(
                score.getUserId(),
                score.getContentId(),
                score.getScore()
        )).collect(Collectors.toList());
        Map<Integer, Map<Integer, Double>> userItemMatrix =
                UserBasedCFUtil.buildUserItemMatrix(houseNewsIds, scoreList);
        UserBasedCFUtil cfUtil = new UserBasedCFUtil(userItemMatrix);
        List<Integer> itemIds = cfUtil.recommendItems(LocalThreadHolder.getUserId(), count);
        if (itemIds.isEmpty()) {
            List<HouseNewsListVO> houseNewsListVOList = queryHouseNews(houseNewsIds.size() > count ? houseNewsIds.subList(0, count) : houseNewsIds);
            return ApiResult.success(houseNewsListVOList);
        }
        List<HouseNewsListVO> houseNewsListVOList = queryHouseNews(itemIds);
        return ApiResult.success(houseNewsListVOList);
    }

    @Override
    public Result<String> increaseViewById(Integer id) {
        HouseNews houseNews = this.getById(id);
        if (houseNews == null) {
            return ApiResult.fail("未找到对应的房屋资讯");
        }
        Integer currentViewNumber = houseNews.getViewNumber();
        if (currentViewNumber == null) {
            currentViewNumber = 0;
        }
        houseNews.setViewNumber(currentViewNumber + 1);
        boolean success = this.updateById(houseNews);
        if (success) {
            return ApiResult.success("浏览量增加成功");
        } else {
            return ApiResult.fail("浏览量增加失败");
        }
    }

    @Override
    public Result<Integer> getViewsById(Integer id) {
        HouseNews houseNews = this.getById(id);
        if (houseNews == null) {
            return ApiResult.fail("未找到对应的房屋资讯");
        }
        Integer viewNumber = houseNews.getViewNumber();
        if (viewNumber == null) {
            viewNumber = 0;
        }
        return ApiResult.success(viewNumber);
    }

    private List<HouseNewsListVO> queryHouseNews(List<Integer> ids) {
        HouseNewsQueryDto houseNewsQueryDto = new HouseNewsQueryDto();
        houseNewsQueryDto.setIds(ids);
        return this.baseMapper.list(houseNewsQueryDto);
    }

}
