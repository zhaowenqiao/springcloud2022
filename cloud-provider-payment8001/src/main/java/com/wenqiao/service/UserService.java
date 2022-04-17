package com.wenqiao.service;

import com.wenqiao.entities.User;

import java.util.List;

public interface UserService {
    List<User> selectUser(User user);
}
