package com.Cathouse.service;

import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.*;
import com.Cathouse.project.entity.User;
import com.Cathouse.project.ValueObject.ChartVO;
import com.Cathouse.project.ValueObject.UserVO;

import java.util.List;

public interface UserService {

    Result<String> register(UserRegisterDto userRegisterDTO);

    Result<Object> login(UserLoginDto userLoginDTO);

    Result<UserVO> auth();

    Result<List<User>> query(UserQueryDto userQueryDto);

    Result<UserVO> update(UserUpdateDto userUpdateDTO);

    Result<String> batchDelete(List<Integer> ids);

    Result<String> updatePassword(UserUpdatePasswordDto userUpdatePasswordDto);

    Result<UserVO> getById(Integer id);

    Result<String> save(UserRegisterDto userRegisterDTO);

    Result<String> backUpdate(User user);

    Result<List<ChartVO>> daysQuery(Integer day);

    Result<String> deleteById(Integer id);

    Result<Boolean> verifyAccount(String account);

    Result<String> updatePasswordA(UpdatePasswordDto updatePasswordDto);
}
