package com.wenqiao.service;

import com.wenqiao.common.CommonListResult;
import com.wenqiao.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "consul-provider-payment")
public interface UserService {

    @PostMapping(value = "/user/selectUserList")
    CommonListResult selectUser(User user);
}
