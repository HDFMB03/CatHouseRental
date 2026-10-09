package com.Cathouse.service;

import com.Cathouse.project.api.Result;
import com.Cathouse.project.ValueObject.ChartVO;

import java.util.List;

public interface DashboardService {

    Result<List<ChartVO>> mainStatic();

    Result<List<ChartVO>> houseLineChart(Integer days);

    Result<List<ChartVO>> cityHouseRange(Integer limit);

}
