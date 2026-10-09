package com.Cathouse.service.impl;

import com.Cathouse.context.LocalThreadHolder;
import com.Cathouse.mapper.UserMapper;
import com.Cathouse.project.api.ApiResult;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.*;
import com.Cathouse.project.EntityManager.RoleEnum;
import com.Cathouse.project.entity.User;
import com.Cathouse.project.ValueObject.ChartVO;
import com.Cathouse.project.ValueObject.TokenResponseVO;
import com.Cathouse.project.ValueObject.UserVO;
import com.Cathouse.service.UserService;
import com.Cathouse.utils.AssertUtils;
import com.Cathouse.utils.DateUtil;
import com.Cathouse.utils.JwtUtil;
import com.Cathouse.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserRegisterDto userRegisterDTO) {
        AssertUtils.hasText(userRegisterDTO.getUsername(), "用户名不能为空");
        AssertUtils.hasText(userRegisterDTO.getAccount(), "账号不能为空");
        AssertUtils.isFalse(ValidationUtils.containsChinese(userRegisterDTO.getAccount()), "账号中不能包含中文");
        User userEntity = userMapper.getUserByUsername(userRegisterDTO.getUsername());
        AssertUtils.isTrue(userEntity == null, "用户已存在");
        User entity = userMapper.getUserByAccount(userRegisterDTO.getAccount());
        AssertUtils.isTrue(entity == null, "账号不可用");
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setRole(RoleEnum.USER.getRole());
        userMapper.save(user);
        return ApiResult.success("注册成功");
    }

    @Override
    public Result<Object> login(UserLoginDto userLoginDTO) {
        AssertUtils.hasText(userLoginDTO.getAccount(), "账号不能为空");
        AssertUtils.hasText(userLoginDTO.getAccount(), "密码不能为空");
        User user = userMapper.getUserByAccount(userLoginDTO.getAccount());
        AssertUtils.isTrue(user != null, "账户不存在");
        AssertUtils.equals(userLoginDTO.getPassword(), user.getPassword(), "密码错误");
        String token = JwtUtil.toToken(user.getId(), user.getRole());
        TokenResponseVO tokenResponseVO = new TokenResponseVO(user.getId(), token, user.getRole());
        return ApiResult.success("登录成功", tokenResponseVO);
    }

    @Override
    public Result<UserVO> auth() {
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ApiResult.success(userVO);
    }

    @Override
    public Result<List<User>> query(UserQueryDto userQueryDto) {
        List<User> users = userMapper.query(userQueryDto);
        Integer count = userMapper.queryCount(userQueryDto);
        return ApiResult.success(users, count);
    }

    @Override
    public Result<UserVO> update(UserUpdateDto userUpdateDTO) {
        if (StringUtils.hasText(userUpdateDTO.getEmail())) {
            if (!ValidationUtils.isValidEmail(userUpdateDTO.getEmail())) {
                return ApiResult.error("请填写正确的邮箱格式");
            }
        }
        if (StringUtils.hasText(userUpdateDTO.getPhone())) {
            AssertUtils.isTrue(
                    ValidationUtils.isValidMobile(userUpdateDTO.getPhone()),
                    "联系电话格式错误，请重新检查"
            );
        }
        User updateEntity = User.builder().id(LocalThreadHolder.getUserId()).build();
        BeanUtils.copyProperties(userUpdateDTO, updateEntity);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updateEntity, userVO);
        userMapper.update(updateEntity);
        return ApiResult.success(userVO);
    }

    @Override
    public Result<String> batchDelete(List<Integer> ids) {
        userMapper.batchDelete(ids);
        return ApiResult.success();
    }

    @Override
    public Result<String> updatePassword(UserUpdatePasswordDto userUpdatePasswordDto) {
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        AssertUtils.equals(
                userUpdatePasswordDto.getNewPassword(),
                userUpdatePasswordDto.getAgainPassword(),
                "前后密码输入不一致"
        );
        AssertUtils.equals(
                user.getPassword(),
                userUpdatePasswordDto.getOldPassword(),
                "原始密码验证失败"
        );
        user.setPassword(userUpdatePasswordDto.getNewPassword());
        userMapper.update(user);
        return ApiResult.success("密码修改成功，请重新登录");
    }

    @Override
    public Result<UserVO> getById(Integer id) {
        User user = userMapper.getUserById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ApiResult.success(userVO);
    }

    @Override
    public Result<String> save(UserRegisterDto userRegisterDTO) {
        return register(userRegisterDTO);
    }

    @Override
    public Result<String> backUpdate(User user) {
        AssertUtils.isTrue(StringUtils.hasText(user.getUsername()), "账号不能为空");
        if (StringUtils.hasText(user.getEmail())) {
            if (!ValidationUtils.isValidEmail(user.getEmail())) {
                return ApiResult.error("请填写正确邮箱格式");
            }
        }
        if (StringUtils.hasText(user.getPhone())) {
            AssertUtils.isTrue(
                    ValidationUtils.isValidMobile(user.getPhone()),
                    "联系电话格式错误，请重新检查"
            );
        }
        userMapper.update(user);
        return ApiResult.success();
    }

    @Override
    public Result<List<ChartVO>> daysQuery(Integer day) {
        QueryDto queryDto = DateUtil.startAndEndTime(day);
        UserQueryDto userQueryDto = new UserQueryDto();
        userQueryDto.setStartTime(queryDto.getStartTime());
        userQueryDto.setEndTime(queryDto.getEndTime());
        List<User> userList = userMapper.query(userQueryDto);
        List<LocalDateTime> localDateTimes = userList.stream().map(User::getCreateTime).collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(day, localDateTimes);
        return ApiResult.success(chartVOS);
    }

    @Override
    public Result<String> deleteById(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        userMapper.batchDelete(ids);
        return ApiResult.success();
    }

    @Override
    public Result<Boolean> verifyAccount(String account) {
        AssertUtils.hasText(account, "账号不能为空");
        User user = userMapper.getUserByAccount(account);
        boolean exists = user != null;
        return ApiResult.success(exists);
    }

    @Override
    public Result<String> updatePasswordA(UpdatePasswordDto updatePasswordDto) {
        AssertUtils.hasText(updatePasswordDto.getAccount(), "账号不能为空");
        AssertUtils.hasText(updatePasswordDto.getPassword(), "密码不能为空");

        User user = userMapper.getUserByAccount(updatePasswordDto.getAccount());
        AssertUtils.isTrue(user != null, "账户不存在");

        user.setPassword(updatePasswordDto.getPassword());
        userMapper.update(user);
        return ApiResult.success("密码修改成功");
    }
}
