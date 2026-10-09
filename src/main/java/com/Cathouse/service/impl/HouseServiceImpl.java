package com.Cathouse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.FlowIndexMapper;
import com.Cathouse.mapper.HouseMapper;
import com.Cathouse.mapper.LandlordMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.FlowIndexQueryDto;
import com.Cathouse.project.DataTransferObject.HouseQueryDto;
import com.Cathouse.project.DataTransferObject.LandlordQueryDto;
import com.Cathouse.project.DataTransferObject.QueryDto;
import com.Cathouse.project.EntityManager.*;
import com.Cathouse.project.entity.FlowIndex;
import com.Cathouse.project.entity.House;
import com.Cathouse.project.ValueObject.*;
import com.Cathouse.service.FlowIndexService;
import com.Cathouse.service.HouseService;
import com.Cathouse.utils.AssertUtils;
import com.Cathouse.utils.DateUtil;
import com.Cathouse.utils.UserBasedCFUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Resource
    private LandlordMapper landlordMapper;
    @Resource
    private FlowIndexService flowIndexService;
    @Resource
    private FlowIndexMapper flowIndexMapper;

    @Override
    public Result<List<HouseListItemVO>> list(HouseQueryDto houseQueryDto) {
        List<HouseListItemVO> houseListItemVOS = this.baseMapper.list(houseQueryDto);
        dealHouseStatus(houseListItemVOS);
        Integer count = this.baseMapper.listCount(houseQueryDto);
        return ApiResult.success(houseListItemVOS, count);
    }

    private void dealHouseStatus(List<HouseListItemVO> houseListItemVOS) {
        for (HouseListItemVO houseListItemVO : houseListItemVOS) {
            if (Objects.nonNull(houseListItemVO.getDirectionId())) {
                String detail = HouseDirectionEnum.getDetail(houseListItemVO.getDirectionId());
                houseListItemVO.setDirectionName(detail);
            }
            if (Objects.nonNull(houseListItemVO.getDepositMethodId())) {
                String detail = HouseDepositEnum.getDetail(houseListItemVO.getDepositMethodId());
                houseListItemVO.setDepositMethodName(detail);
            }
            if (Objects.nonNull(houseListItemVO.getFitmentStatusId())) {
                String detail = HouseFitmentEnum.getDetail(houseListItemVO.getFitmentStatusId());
                houseListItemVO.setFitmentStatusName(detail);
            }
        }
    }

    private void dealHouseVOStatus(List<HouseVO> houseVOS) {
        for (HouseVO houseVO : houseVOS) {
            if (Objects.nonNull(houseVO.getDirectionId())) {
                String detail = HouseDirectionEnum.getDetail(houseVO.getDirectionId());
                houseVO.setDirectionName(detail);
            }
            if (Objects.nonNull(houseVO.getDepositMethodId())) {
                String detail = HouseDepositEnum.getDetail(houseVO.getDepositMethodId());
                houseVO.setDepositMethodName(detail);
            }
            if (Objects.nonNull(houseVO.getFitmentStatusId())) {
                String detail = HouseFitmentEnum.getDetail(houseVO.getFitmentStatusId());
                houseVO.setFitmentStatusName(detail);
            }
            if (Objects.nonNull(houseVO.getTypeId())) {
                String detail = HouseTypeEnum.getDetail(houseVO.getTypeId());
                houseVO.setTypeName(detail);
            }
            if (Objects.nonNull(houseVO.getSizedId())) {
                String detail = HouseSizedEnum.getDetail(houseVO.getSizedId());
                houseVO.setSizedName(detail);
            }
            if (Objects.nonNull(houseVO.getRentalType())) {
                String detail = RentalTypeEnum.getDetail(houseVO.getRentalType());
                houseVO.setRentalTypeName(detail);
            }
        }
    }

    @Override
    public Result<String> update(House house) {
        paramJudge(house, true);
        updateById(house);
        return ApiResult.success("房屋信息修改成功");
    }
    private void paramJudge(House house) {
        paramJudge(house, false);
    }
    private void paramJudge(House house, boolean isUpdate) {
        AssertUtils.hasText(house.getName(), "房屋名称不能为空");
        AssertUtils.hasText(house.getCover(), "房屋封面不能为空");
        AssertUtils.hasText(house.getFloor(), "请填写楼层");
        AssertUtils.hasText(house.getCovers(), "房屋实况图不能为空");
        AssertUtils.notNull(house.getTypeId(), "请设置房屋类型");
        AssertUtils.notNull(house.getSizeNumber(), "请填写房屋面积");
        AssertUtils.notNull(house.getDirectionId(), "请设置房屋朝向");
        AssertUtils.notNull(house.getSizedId(), "请设置户型");
        AssertUtils.notNull(house.getRent(), "请设置租金");
        AssertUtils.notNull(house.getRentalHumen(), "请设置租赁人员");
        AssertUtils.notNull(house.getDepositMethodId(), "请设置押金方式");
        AssertUtils.notNull(house.getIsSubway(), "请设置是否临近地铁");
        if (house.getIsSubway()) {
            AssertUtils.notNull(house.getSubwayLine(), "请设置地铁线路");
        }
        AssertUtils.notNull(house.getFitmentStatusId(), "请设置装修类型");
        AssertUtils.notNull(house.getRentalType(), "请设置租赁方式");
        AssertUtils.isTrue(house.getName().length() < 30, "房屋名称最多30个字");
        checkHouseNameDuplicate(house.getName(), house.getId(), isUpdate);
        String covers = house.getCovers();
        long count = covers.chars().filter(c -> c == ',').count();
        AssertUtils.isTrue(count < 20, "实况图最多上传20张");
        AssertUtils.isTrue(house.getFloor().length() <= 2, "楼层最多2个字");
    }
    private void checkHouseNameDuplicate(String houseName, Integer houseId, boolean isUpdate) {
        HouseQueryDto queryDto = new HouseQueryDto();
        queryDto.setName(houseName.trim());
        List<HouseListItemVO> existingHouses = this.baseMapper.list(queryDto);
        if (existingHouses != null && !existingHouses.isEmpty()) {
            if (isUpdate && houseId != null) {
                boolean hasOtherSameName = existingHouses.stream().anyMatch(house -> !house.getId().equals(houseId));
                    AssertUtils.isFalse(hasOtherSameName, "该房屋信息已存在，请勿重复添加");
                    } else {
                AssertUtils.isFalse(true, "该房屋信息已存在，请勿重复添加");
            }
        }
    }

    @Override
    public Result<String> saveEntity(House house) {
        paramJudge(house);
        LandlordVO landlordVO = getLandlord();
        AssertUtils.isTrue(landlordVO.getIsAudit(), "房东认证信息待审核中，请稍后再试");
        house.setLandlordId(landlordVO.getId());
        house.setCreateTime(LocalDateTime.now());
        house.setStatus(HouseStatusEnum.STATUS_1.getType());
        save(house);
        return ApiResult.success("房屋新增成功");
    }
    private LandlordVO getLandlord() {
        LandlordQueryDto landlordQueryDto = new LandlordQueryDto();
        landlordQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = landlordMapper.list(landlordQueryDto);
        AssertUtils.isFalse(landlordVOS.isEmpty(), "房东信息异常，非法操作");
        return landlordVOS.get(0);
    }
    @Override
    public Result<List<SelectedVO>> houseTypeList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseTypeEnum.values())
                .map(houseTypeEnum -> new SelectedVO(houseTypeEnum.getType(), houseTypeEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseDirectionList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseDirectionEnum.values())
                .map(houseDirectionEnum -> new SelectedVO(houseDirectionEnum.getType(), houseDirectionEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseSizedList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseSizedEnum.values())
                .map(houseSizedEnum -> new SelectedVO(houseSizedEnum.getType(), houseSizedEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseDepositMethodList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseDepositEnum.values())
                .map(houseDepositEnum -> new SelectedVO(houseDepositEnum.getType(), houseDepositEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseSubwayList() {
        List<SelectedVO> selectedVOS = Arrays.stream(IsSubwayEnum.values())
                .map(isSubwayEnum -> new SelectedVO(isSubwayEnum.getType(), isSubwayEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseFitmentStatusList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseFitmentEnum.values())
                .map(houseFitmentEnum -> new SelectedVO(houseFitmentEnum.getType(), houseFitmentEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseRentalTypeList() {
        List<SelectedVO> selectedVOS = Arrays.stream(RentalTypeEnum.values())
                .map(rentalTypeEnum -> new SelectedVO(rentalTypeEnum.getType(), rentalTypeEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<LivingFacilityVO>> houseLivingFacilityList() {
        List<LivingFacilityVO> livingFacilityVOS = Arrays.stream(LivingFacilitiesEnum.values())
                .map(livingFacilitiesEnum -> new LivingFacilityVO(livingFacilitiesEnum.getType(), livingFacilitiesEnum.getSelected()))
                .collect(Collectors.toList());
        return ApiResult.success(livingFacilityVOS);
    }

    @Override
    public Result<String> houseStatusDeal(Integer id) {
        AssertUtils.notNull(id, "房源ID不能为空");
        House house = getById(id);
        AssertUtils.notNull(house, "房源信息异常");
        AssertUtils.notNull(house.getLandlordId(), "房源信息异常");
        boolean equals = Objects.equals(house.getStatus(), HouseStatusEnum.STATUS_1.getType());
        house.setStatus(equals
                ? HouseStatusEnum.STATUS_2.getType()
                : HouseStatusEnum.STATUS_1.getType());
        if (Objects.equals(LocalThreadHolder.getRoleId(), RoleEnum.ADMIN.getRole())) {
            updateById(house);
            return ApiResult.success(equals ? "房屋已下架" : "房屋已上架");
        }
        Integer landlordId = house.getLandlordId();
        LandlordVO landlord = getLandlord();
        AssertUtils.notNull(landlord, "房东信息异常");
        AssertUtils.isTrue(landlord.getIsAudit(), "房东信息未审核");
        AssertUtils.equals(landlordId, landlord.getId(), "非法操作");
        updateById(house);
        return ApiResult.success(equals ? "房屋已下架" : "房屋已上架");
    }

    @Override
    public Result<HouseVO> selectById(Integer id) {
        HouseVO houseVO = this.baseMapper.getById(id);
        List<HouseVO> houseVOList = new ArrayList<>();
        houseVOList.add(houseVO);
        dealHouseVOStatus(houseVOList);
        return ApiResult.success(houseVO);
    }

    @Override
    public Result<List<SelectedVO>> houseSizeNumber() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseSizeNumberEnum.values())
                .map(houseSizeNumberEnum -> new SelectedVO(houseSizeNumberEnum.getType(), houseSizeNumberEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<SelectedVO>> houseRentRange() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseRentEnum.values())
                .map(houseRentEnum -> new SelectedVO(houseRentEnum.getType(), houseRentEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    @Override
    public Result<List<HouseListItemVO>> listUser(HouseQueryDto houseQueryDto) {
        houseQueryDto.setStatus(HouseStatusEnum.STATUS_1.getType());
        List<HouseListItemVO> houseListItemVOS = this.baseMapper.list(houseQueryDto);
        dealHouseStatus(houseListItemVOS);
        dealFlowIndex(houseListItemVOS);
        Integer count = this.baseMapper.listCount(houseQueryDto);
        return ApiResult.success(houseListItemVOS, count);
    }

    private void dealFlowIndex(List<HouseListItemVO> houseListItemVOS) {
        List<FlowIndex> flowIndexList = new ArrayList<>();
        for (HouseListItemVO houseListItemVO : houseListItemVOS) {
            FlowIndex flowIndex = new FlowIndex();
            flowIndex.setCreateTime(LocalDateTime.now());
            flowIndex.setContentType("HOUSE_INFO");
            flowIndex.setType(FlowIndexEnum.SHOW.getType());
            flowIndex.setContentId(houseListItemVO.getId());
            flowIndex.setUserId(LocalThreadHolder.getUserId() == null ? -1 : LocalThreadHolder.getUserId());
            flowIndexList.add(flowIndex);
        }
        flowIndexService.saveBatch(flowIndexList);
        flowIndexList.clear();
    }

    @Override
    public Result<List<HouseFlowIndexVO>> listFlowIndex(HouseQueryDto houseQueryDto) {
        LandlordVO landlord = getLandlord();
        AssertUtils.isTrue(landlord.getIsAudit(), "房东认证信息待审核，请稍后再试");
        List<Integer> houseIds = this.baseMapper.selectIdsByLandlordId(landlord.getId());
        if (houseIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        HouseQueryDto queryDto = new HouseQueryDto();
        queryDto.setIds(houseIds);
        List<HouseFlowIndexVO> houseFlowIndexVOS = this.baseMapper.flowIndexList(queryDto);
        dealClickRate(houseFlowIndexVOS);
        Integer count = this.baseMapper.listCount(queryDto);
        return ApiResult.success(houseFlowIndexVOS, count);
    }

    @Override
    public Result<List<HouseListItemVO>> landlordHouseList(HouseQueryDto houseQueryDto) {
        LandlordVO landlordVO = getLandlord();
        houseQueryDto.setLandlordId(landlordVO.getId());
        List<HouseListItemVO> houseListItemVOS = this.baseMapper.list(houseQueryDto);
        dealHouseStatus(houseListItemVOS);
        Integer count = this.baseMapper.listCount(houseQueryDto);
        return ApiResult.success(houseListItemVOS, count);
    }

    private void dealClickRate(List<HouseFlowIndexVO> houseFlowIndexVOS) {
        if (houseFlowIndexVOS == null || houseFlowIndexVOS.isEmpty()) {
            return;
        }
        for (HouseFlowIndexVO vo : houseFlowIndexVOS) {
            if (vo.getShowNumber() != null && vo.getViewNumber() != null
                    && vo.getShowNumber() != 0) {
                double rate = (double) vo.getViewNumber() / vo.getShowNumber() * 100;
                vo.setClickRate(Double.parseDouble(String.format("%.2f", rate)));
            } else {
                vo.setClickRate(0.0);
            }
        }
    }

    @Override
    public Result<List<ChartVO>> listChart(HouseQueryDto houseQueryDto) {
        flowIndexListParamJudge(houseQueryDto);
        FlowIndexQueryDto flowIndexQueryDto = createFlowIndexQueryDto(houseQueryDto);
        List<FlowIndex> flowIndexList = flowIndexMapper.list(flowIndexQueryDto);
        if (flowIndexList == null || flowIndexList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        List<LocalDateTime> dateTimeList = flowIndexList.stream()
                .map(FlowIndex::getCreateTime)
                .collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(houseQueryDto.getDays(), dateTimeList);
        return ApiResult.success(chartVOS);
    }

    private void flowIndexListParamJudge(HouseQueryDto houseQueryDto) {
        AssertUtils.notNull(houseQueryDto, "查询条件不能为空");
        AssertUtils.notNull(houseQueryDto.getId(), "内容ID不能为空");
        AssertUtils.notNull(houseQueryDto.getType(), "流量类型不能为空");
        AssertUtils.notNull(houseQueryDto.getDays(), "查询天数不能为空");
    }

    private FlowIndexQueryDto createFlowIndexQueryDto(HouseQueryDto houseQueryDto) {
        FlowIndexQueryDto flowIndexQueryDto = new FlowIndexQueryDto();
        flowIndexQueryDto.setContentType("HOUSE_INFO");
        flowIndexQueryDto.setContentId(houseQueryDto.getId());
        flowIndexQueryDto.setType(houseQueryDto.getType());
        QueryDto queryDto = DateUtil.startAndEndTime(houseQueryDto.getDays());
        flowIndexQueryDto.setStartTime(queryDto.getStartTime());
        flowIndexQueryDto.setEndTime(queryDto.getEndTime());
        return flowIndexQueryDto;
    }

    @Override
    public Result<List<HouseListItemVO>> recommend(Integer count) {
        AssertUtils.notNull(count, "推荐条数不能为空");
        FlowIndexQueryDto flowIndexQueryDto = new FlowIndexQueryDto();
        flowIndexQueryDto.setContentType("HOUSE_INFO");
        List<Integer> houseIds = this.baseMapper.getIds();
        if (houseIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        List<ScoreVO> scores = flowIndexMapper.getScores(flowIndexQueryDto);
        if (scores.isEmpty()) {
            List<HouseListItemVO> houseListItemVOS = queryHouseNews(houseIds.size() > count ? houseIds.subList(0, count) : houseIds);
            return ApiResult.success(houseListItemVOS);
        }
        List<UserBasedCFUtil.Score> scoreList = scores.stream().map(score -> new UserBasedCFUtil.Score(
                score.getUserId(),
                score.getContentId(),
                score.getScore()
        )).collect(Collectors.toList());
        Map<Integer, Map<Integer, Double>> userItemMatrix =
                UserBasedCFUtil.buildUserItemMatrix(houseIds, scoreList);
        UserBasedCFUtil cfUtil = new UserBasedCFUtil(userItemMatrix);
        List<Integer> itemIds = cfUtil.recommendItems(LocalThreadHolder.getUserId(), count);
        if (itemIds.isEmpty()) {
            List<HouseListItemVO> houseListItemVOS = queryHouseNews(houseIds.size() > count ? houseIds.subList(0, count) : houseIds);
            return ApiResult.success(houseListItemVOS);
        }
        List<HouseListItemVO> houseListItemVOS = queryHouseNews(itemIds);
        return ApiResult.success(houseListItemVOS);
    }

    @Override
    public Result<List<SelectedVO>> rentalHumanList() {
        List<SelectedVO> selectedVOS = Arrays.stream(RentalHumanEnum.values())
                .map(rentalHumanEnum -> new SelectedVO(rentalHumanEnum.getType(), rentalHumanEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    private List<HouseListItemVO> queryHouseNews(List<Integer> ids) {
        HouseQueryDto houseQueryDto = new HouseQueryDto();
        houseQueryDto.setIds(ids);
        return this.baseMapper.list(houseQueryDto);
    }


}
