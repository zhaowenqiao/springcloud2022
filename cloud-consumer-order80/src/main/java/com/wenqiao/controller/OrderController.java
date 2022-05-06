package com.wenqiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenqiao.common.CommonListResult;

import com.wenqiao.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
//    private static final String url = "http://localhost:8001/user/selectUserList";//单机
    private static final String url = "http://cloud-payment-service/user/selectUserList";//集群
    @Resource
    private RestTemplate restTemplate;

    /**
     * 以application/json  格式JSON调用
     * @param user
     * @return
     */
    @PostMapping("/selectOrder")
    public CommonListResult create(@RequestBody User user) {
        log.info("the method is start,param is:{}", JSONObject.toJSON(user));
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("username", user.getUsername());
        HttpEntity<String> formEntity = new HttpEntity<>(jsonObj.toString(), headers);
        CommonListResult result = restTemplate.postForObject(url, formEntity, CommonListResult.class);
        return result;
    }

    /**
     * application/x-www-form-urlencoded  表单
     * @param user
     * @return
     */
    @PostMapping("/selectOrder2")
    public CommonListResult create2(@RequestBody User user) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", user.getUsername());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
        CommonListResult responseMessage = restTemplate.postForObject(url, r, CommonListResult.class);
        return responseMessage;
    }

}
