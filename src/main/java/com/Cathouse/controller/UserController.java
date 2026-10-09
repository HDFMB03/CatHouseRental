package com.Cathouse.controller;

import com.Cathouse.aop.Pager;
import com.Cathouse.project.api.Result;
import com.Cathouse.project.DataTransferObject.*;
import com.Cathouse.project.entity.User;
import com.Cathouse.project.ValueObject.UserVO;
import com.Cathouse.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/login")
    @ResponseBody
    public Result<Object> login(@RequestBody UserLoginDto userLoginDTO) {
        return userService.login(userLoginDTO);
    }

    @GetMapping(value = "/auth")
    @ResponseBody
    public Result<UserVO> auth() {
        return userService.auth();
    }

    @GetMapping(value = "/getById/{id}")
    @ResponseBody
    public Result<UserVO> getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public Result<String> register(@RequestBody UserRegisterDto userRegisterDTO) {
        return userService.register(userRegisterDTO);
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public Result<String> save(@RequestBody UserRegisterDto userRegisterDTO) {
        return userService.save(userRegisterDTO);
    }

    @PutMapping(value = "/update")
    @ResponseBody
    public Result<UserVO> update(@RequestBody UserUpdateDto userUpdateDTO) {
        return userService.update(userUpdateDTO);
    }

    @PutMapping(value = "/backUpdate")
    @ResponseBody
    public Result<String> backUpdate(@RequestBody User user) {
        return userService.backUpdate(user);
    }

    @PutMapping(value = "/updatePassword")
    @ResponseBody
    public Result<String> updatePassword(@RequestBody UserUpdatePasswordDto userUpdatePasswordDto) {
        return userService.updatePassword(userUpdatePasswordDto);
    }

    @PutMapping(value = "/updatePasswordA")
    @ResponseBody
    public Result<String> updatePasswordA(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return userService.updatePasswordA(updatePasswordDto);
    }

    @GetMapping(value = "/verifyAccount")
    @ResponseBody
    public Result<Boolean> verifyAccount(@RequestParam String account) {
        return userService.verifyAccount(account);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public Result<String> deleteById(@PathVariable Integer id) {
        return userService.deleteById(id);
    }

    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<User>> query(@RequestBody UserQueryDto userQueryDto) {
        return userService.query(userQueryDto);
    }

}

