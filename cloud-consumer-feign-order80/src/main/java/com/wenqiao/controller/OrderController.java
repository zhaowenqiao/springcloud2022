package com.wenqiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import com.wenqiao.common.CommonListResult;
import com.wenqiao.entities.User;
import com.wenqiao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private UserService userService;

    /**
     * 以application/json  格式JSON调用
     * @param user
     * @return
     */
    @PostMapping("/selectOrder")
    public CommonListResult create(@RequestBody User user) {
        log.info("the method is start,param is:{}", JSONObject.toJSON(user));
        CommonListResult result = userService.selectUser(user);
        return result;
    }

}
