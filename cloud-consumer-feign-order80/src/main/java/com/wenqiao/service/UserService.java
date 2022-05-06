package com.wenqiao.service;

import com.wenqiao.common.CommonListResult;
import com.wenqiao.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "cloud-payment-service")
public interface UserService {
    /**
     * 根据id查询
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/user/selectUserList")
    CommonListResult selectUser(User user);
}
