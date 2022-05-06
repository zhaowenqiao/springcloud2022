package com.wenqiao.controller;

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
    public String paymentInfo_TimeOut() {
        return paymentHystrixService.paymentInfo_TimeOut();
    }

}
