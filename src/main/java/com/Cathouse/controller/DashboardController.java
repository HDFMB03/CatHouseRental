package com.Cathouse.controller;

import com.Cathouse.project.api.Result;
import com.Cathouse.project.ValueObject.ChartVO;
import com.Cathouse.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Resource
    private DashboardService dashboardService;

    @GetMapping(value = "/mainStatic")
    @ResponseBody
    public Result<List<ChartVO>> mainStatic() {
        return dashboardService.mainStatic();
    }

    @GetMapping(value = "/houseLineChart/{days}")
    @ResponseBody
    public Result<List<ChartVO>> houseLineChart(@PathVariable Integer days) {
        return dashboardService.houseLineChart(days);
    }

    @GetMapping(value = "/cityHouseRange/{limit}")
    @ResponseBody
    public Result<List<ChartVO>> cityHouseRange(@PathVariable Integer limit) {
        return dashboardService.cityHouseRange(limit);
    }

}

