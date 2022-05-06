package com.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这个类 不能放在SpringBootApplication能扫的那一层
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        return new RandomRule();//随机
    }
}