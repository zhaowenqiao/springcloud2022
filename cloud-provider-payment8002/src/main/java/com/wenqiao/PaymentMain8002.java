package com.wenqiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @ClassName: PaymentMain8001
 * @description: PaymentMain8001主启动类
 * @author: zwq
 * @create: 2020/3/5 17:30
 **/
@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8002 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8002.class, args);
    }
}
