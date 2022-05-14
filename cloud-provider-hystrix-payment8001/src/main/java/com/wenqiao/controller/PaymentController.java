package com.wenqiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wenqiao.common.CommonResult;
import com.wenqiao.dto.FileDTO;
import com.wenqiao.entities.User;
import com.wenqiao.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String SERVER_PORT;

    /**
     * 正常访问
     *
     * @param user
     * @return
     */
    @PostMapping("/getUser")
    public String getUser(@RequestBody User user) {
        log.info("***user is:{}" , user);
        return JSONObject.toJSONString(user);
    }

    /**
     * 正常访问
     *
     * @return
     */
    @PostMapping("/hystrix/ok")
    public String paymentInfo_OK() {
        final String result = paymentService.paymentInfo_OK(1);
        log.info("***result:" + result);
        return result;
    }

    /**
     * 非正常访问
     *
     * @return
     */
    @PostMapping("/hystrix/timeout")
    public String paymentInfo_TimeOut() {
        final String result = paymentService.paymentInfo_TimeOut(1);
        log.info("***result:" + result);
        return result;
    }

    /**
     * 非正常访问
     * @param fileDTO 文件类的DTO
     * @return
     */
    @PostMapping("/uploadFile")
    public CommonResult uploadFile(FileDTO fileDTO) throws Exception{
        log.info("uploadFile is:{}", fileDTO.getFileType());
        MultipartFile file = fileDTO.getFile();
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片
        String filePath = "D:\\File\\";
        file.transferTo(new File(filePath+fileName));
        return new CommonResult("200","处理成功");
    }


    /**
     * 服务熔断
     * http://localhost:8001/payment/circuit/1
     *
     * @param id
     * @return
     */
    @PostMapping("/circuit/{id}")
    @HystrixCommand
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("***result:" + result);
        return result;
    }


}
