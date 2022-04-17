package com.wenqiao.mapper;

import com.wenqiao.entities.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectUser(User user);
}
