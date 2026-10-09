package com.Cathouse.mapper;

import com.Cathouse.project.DataTransferObject.UserQueryDto;
import com.Cathouse.project.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int save(User userInsert);

    List<User> query(UserQueryDto userQueryDto);

    int queryCount(UserQueryDto userQueryDto);

    int update(User user);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    User getUserById(@Param(value = "id") Integer id);

    User getUserByAccount(@Param(value = "account") String account);

    User getUserByUsername(@Param(value = "username") String account);

}
