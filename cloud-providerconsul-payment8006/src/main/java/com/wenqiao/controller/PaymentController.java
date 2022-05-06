package com.wenqiao.controller;

import com.wenqiao.common.CommonListResult;
import com.wenqiao.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/selectUserList")
    public CommonListResult paymentZK() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("12323");
        user.setCreate_time("20220426");
        user.setUsername("张无忌");
        user.setPassword("mima");
        user.setUpdate_time("20220426");
        userList.add(user);
        return new CommonListResult("200",port + "处理成功",userList);
    }
}

