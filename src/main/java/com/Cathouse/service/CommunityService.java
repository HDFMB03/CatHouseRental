package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.CommunityQueryDto;
import com.Cathouse.project.entity.Community;
import com.Cathouse.project.ValueObject.CommunityVO;

import java.util.List;

public interface CommunityService extends IService<Community> {

    Result<List<CommunityVO>> list(CommunityQueryDto communityQueryDto);

    Result<String> update(Community community);

    Result<String> saveEntity(Community community);

    Result<CommunityVO> selectById(Integer id);

}
