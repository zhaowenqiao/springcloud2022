package com.wenqiao.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wenqiao.service.PaymentHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class OrderHystrixController {
    @Autowired
    private PaymentHystrixService paymentHystrixService;

    /**
     * 正常访问
     * http://localhost/consumer/payment/hystrix/ok/1
     *
     * @return
     */
    @PostMapping("/payment/hystrix/ok")
    public String paymentInfo_OK() {
        return paymentHystrixService.paymentInfo_OK();
    }

    /**
     * 超时访问
     *
     * @return
     */
    @PostMapping("/payment/hystrix/timeout")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public String paymentInfo_TimeOut() {
        return paymentHystrixService.paymentInfo_TimeOut();
    }

    private String paymentTimeOutFallbackMethod() {
        return "消费者80，对方支付系统繁忙，或自己运行出错请检查自己，请10秒后再试。";
    }
}
