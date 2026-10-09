package com.Cathouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.HouseNewsQueryDto;
import com.Cathouse.project.entity.HouseNews;
import com.Cathouse.project.ValueObject.HouseNewsListVO;

import java.util.List;

public interface HouseNewsService extends IService<HouseNews> {

    Result<List<HouseNewsListVO>> list(HouseNewsQueryDto houseNewsQueryDto);

    Result<String> saveEntity(HouseNews houseNews);

    Result<HouseNews> selectById(Integer id);

    Result<String> updateEntity(HouseNews houseNews);

    Result<List<HouseNewsListVO>> recommend(Integer count);

    Result<String> increaseViewById(Integer id);

    Result<Integer> getViewsById(Integer id);

}
