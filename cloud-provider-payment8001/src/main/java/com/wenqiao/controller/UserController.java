package com.wenqiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenqiao.common.CommonListResult;
import com.wenqiao.entities.User;
import com.wenqiao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/selectUserList")
    public CommonListResult create(@RequestBody User user) {
        log.info("the method is start,param is:{}", JSONObject.toJSON(user));
        List<User> userList = userService.selectUser(user);
        return new CommonListResult(userList);
    }
}
