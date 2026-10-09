package com.Cathouse.service.impl;

import com.Cathouse.utils.IdCardValidator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.LandlordMapper;
import com.Cathouse.mapper.UserMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.LandlordQueryDto;
import com.Cathouse.project.EntityManager.IsAuditEnum;
import com.Cathouse.project.EntityManager.RoleEnum;
import com.Cathouse.project.entity.Landlord;
import com.Cathouse.project.entity.User;
import com.Cathouse.project.ValueObject.LandlordVO;
import com.Cathouse.service.LandlordService;
import com.Cathouse.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LandlordServiceImpl extends ServiceImpl<LandlordMapper, Landlord> implements LandlordService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result<List<LandlordVO>> list(LandlordQueryDto landlordQueryDto) {
        List<LandlordVO> landlordVOS = this.baseMapper.list(landlordQueryDto);
        Integer count = this.baseMapper.listCount(landlordQueryDto);
        return ApiResult.success(landlordVOS, count);
    }

    @Override
    public Result<String> update(Landlord landlord) {
        if (landlord.getIsAudit()) {
            User user = userMapper.getUserById(LocalThreadHolder.getUserId());
            AssertUtils.equals(user.getRole(), RoleEnum.ADMIN.getRole(), "无操作权限");
        }
        updateById(landlord);
        return ApiResult.success("修改成功");
    }

    @Override
    public Result<String> saveEntity(Landlord landlord) {
        AssertUtils.hasText(landlord.getIdcardFront(), "请上传身份证正面照");
        AssertUtils.hasText(landlord.getIdcardOpposite(), "请上传身份证反面照");
        AssertUtils.hasText(landlord.getIdcard(), "请上传身份证号");
        if (!IdCardValidator.validate(landlord.getIdcard())) {
            return ApiResult.error("身份证号码格式不正确，请重新输入");
        }
        LandlordQueryDto landlordQueryDto = new LandlordQueryDto();
        landlordQueryDto.setUserId(LocalThreadHolder.getUserId());
        Integer count = this.baseMapper.listCount(landlordQueryDto);
        AssertUtils.isTrue(count == 0, "请勿重复申请");
        landlord.setUserId(LocalThreadHolder.getUserId());
        landlord.setCreateTime(LocalDateTime.now());
        landlord.setIsAudit(IsAuditEnum.STATUS_2.getType());
        save(landlord);
        return ApiResult.success("申请成功，请等待审核");
    }

    @Override
    public Result<LandlordVO> listUser(LandlordQueryDto landlordQueryDto) {
        landlordQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = this.baseMapper.list(landlordQueryDto);
        if (landlordVOS.isEmpty()) {
            return ApiResult.success();
        }
        LandlordVO landlordVO = landlordVOS.get(0);
        String idcard = landlordVO.getIdcard();
        String idcardCode = idcard.substring(0, 6) + "****" + idcard.substring(idcard.length() - 2);
        landlordVO.setIdcard(idcardCode);
        return ApiResult.success(landlordVO);
    }

}
