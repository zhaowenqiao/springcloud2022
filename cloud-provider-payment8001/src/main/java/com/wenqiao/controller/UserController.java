package com.wenqiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenqiao.common.CommonListResult;
import com.wenqiao.entities.User;
import com.wenqiao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @Value("${server.port}")
    private String port;

    @PostMapping("/selectUserList")
    public CommonListResult create(@RequestBody User user) {
        log.info("the method is start,param is:{}", JSONObject.toJSON(user));
        List<User> userList = userService.selectUser(user);
        //模拟超时
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new CommonListResult("200",port + "处理成功",userList);
    }
}
