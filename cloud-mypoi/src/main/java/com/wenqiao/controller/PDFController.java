package com.wenqiao.controller;

import com.wenqiao.utils.PdfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;



@RestController
@RequestMapping("/pdf")
@Slf4j
public class PDFController {

    @PostMapping("/filePDF")
    public void createPDF() {
        Map<String, String> data = new LinkedHashMap();
//        MypdfDTO mypdfDTO = new MypdfDTO();

        //      文本域------填充的内容
        data.put("name","张三");
        data.put("sex", "男");
        data.put("age","18");
        data.put("date","2022年05月11日");

        //文件模板位置
        String templatePdfPath = "D:\\createpdfpicture\\test33.pdf";
        //填充后的模板位置
        String destPdfPath = "D:\\createpdfpicture\\afterTest.pdf";//注这个文件是没有设置文本域的
        //储存图片
        Map imgURlMap = new LinkedHashMap();//需要图片直接加入参数即可
        //             公章文本域----------公章图片地址
        imgURlMap.put("gongzhang","D:\\createpdfpicture\\gongzhang.jpg");

        try {
            PdfUtils.genPdf(data,templatePdfPath,destPdfPath,imgURlMap);
        } catch (IOException e) {
            log.info("genPdf error:" + e);
        }

    }
}
