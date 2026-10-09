package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.mapper.CommunityMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.CommunityQueryDto;
import com.Cathouse.project.entity.Community;
import com.Cathouse.project.ValueObject.CommunityVO;
import com.Cathouse.service.CommunityService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Override
    public Result<List<CommunityVO>> list(CommunityQueryDto communityQueryDto) {
        List<CommunityVO> communityVOS = this.baseMapper.list(communityQueryDto);
        Integer count = this.baseMapper.listCount(communityQueryDto);
        return ApiResult.success(communityVOS, count);
    }

    @Override
    public Result<String> update(Community community) {
        paramJudge(community, true);
        updateById(community);
        return ApiResult.success("修改成功");
    }

    private void paramJudge(Community community) {
        paramJudge(community, false);
    }

    private void paramJudge(Community community, boolean isUpdate) {
        AssertUtils.hasText(community.getName(), "小区名不能为空");
        AssertUtils.hasText(community.getCover(), "小区封面图不能为空");
        AssertUtils.hasText(community.getCovers(), "小区实况图不能为空");
        AssertUtils.hasText(community.getDetail(), "小区详情不能为空");
        AssertUtils.notNull(community.getAreaId(), "小区所属地区不能为空");
        AssertUtils.isTrue(community.getName().length() < 30, "小区名不能超过30个字");
        checkCommunityNameDuplicate(community.getName(), community.getId(), isUpdate);
    }

    private void checkCommunityNameDuplicate(String communityName, Integer communityId, boolean isUpdate) {
        CommunityQueryDto queryDto = new CommunityQueryDto();
        queryDto.setName(communityName.trim());
        List<CommunityVO> existingCommunities = this.baseMapper.list(queryDto);
        if (existingCommunities != null && !existingCommunities.isEmpty()) {
            if (isUpdate && communityId != null) {
                boolean hasOtherSameName = existingCommunities.stream()
                        .anyMatch(community -> !community.getId().equals(communityId));
                AssertUtils.isFalse(hasOtherSameName, "该小区已存在，请勿重复添加");
            } else {
                AssertUtils.isFalse(true, "该小区已存在，请勿重复添加");
            }
        }
    }
    @Override
    public Result<String> saveEntity(Community community) {
        paramJudge(community);
        community.setCreateTime(LocalDateTime.now());
        save(community);
        return ApiResult.success("新增成功");
    }

    @Override
    public Result<CommunityVO> selectById(Integer id) {
        AssertUtils.notNull(id, "小区ID不能为空");
        CommunityQueryDto communityQueryDto = new CommunityQueryDto();
        communityQueryDto.setId(id);
        List<CommunityVO> communityVOS = this.baseMapper.list(communityQueryDto);
        return ApiResult.success(communityVOS.isEmpty() ? new CommunityVO() : communityVOS.get(0));
    }
}
