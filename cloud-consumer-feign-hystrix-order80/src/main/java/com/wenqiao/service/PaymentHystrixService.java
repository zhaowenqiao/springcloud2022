package com.wenqiao.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(value = "cloud-provider-hystrix-payment")
public interface PaymentHystrixService {

    /**
     * 正常访问
     *
     * @return
     */
    @PostMapping("/payment/hystrix/ok")
    String paymentInfo_OK();

    /**
     * 非正常访问
     *
     * @return
     */
    @PostMapping("/payment/hystrix/timeout")
    String paymentInfo_TimeOut() ;
}
