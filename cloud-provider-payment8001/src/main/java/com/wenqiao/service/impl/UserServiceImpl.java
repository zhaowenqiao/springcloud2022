package com.wenqiao.service.impl;

import com.wenqiao.entities.User;
import com.wenqiao.mapper.UserMapper;
import com.wenqiao.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> selectUser(User user) {
        return userMapper.selectUser11(user);
    }
}
