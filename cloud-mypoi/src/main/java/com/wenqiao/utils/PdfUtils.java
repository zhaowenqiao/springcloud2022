package com.wenqiao.utils;


import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 这种方式是先生成pdf,然后再对pdf做文本域填充
 */
public class PdfUtils {

    /**
     * @param map 需要填充的字段
     * @param sourceFile  原文件路径
     * @param targetFile  目标文件路径
     * @param imgURlMap    填充图片路径
     * @throws IOException
     */
    public static void genPdf(Map<String, String> map, String sourceFile, String targetFile, Map imgURlMap) throws IOException {
        File templateFile = new File(sourceFile);
        fillParam(map, FileUtils.readFileToByteArray(templateFile), targetFile,imgURlMap);
    }

    /**
     *使用map中的参数填充pdf，map中的key和pdf表单中的field对应
     * @param fieldValueMap
     * @param file
     * @param contractFileName
     * @param imgURLMap //这边暂时吧图片给注释了，如需填充图片直接加参数即可
     */
    public static  void fillParam(Map<String, String> fieldValueMap,byte[] file, String contractFileName, Map<String,String> imgURLMap) {
        //输出流
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(contractFileName);
            //获取PdfReader对象,获取模板位置
            PdfReader reader = null;
            /* 将要生成的目标PDF文件名称 */
            PdfStamper stamper = null;
            BaseFont base = null;
            //取出模板中的所有字段
            AcroFields acroFields = null;
            // 获取存在resources目录下的pdf模板位置 URL
//            URL file2 = PdfUtils.class.getClassLoader().getResource("templates/test33.pdf");
            try {
                reader = new PdfReader(file);
                stamper = new PdfStamper(reader, fos);
                stamper.setFormFlattening(true);
                //简体中文字体
//                base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                acroFields = stamper.getAcroFields();

                //图片
                // 这个指的是模板中对应位置的名字
                String fieldname = "tupian";
                // 图片路径
                String imagepath = "D:\\createpdfpicture\\gongzhang.jpg";
                // 提取pdf中的表单
                AcroFields form = stamper.getAcroFields();
//                form.addSubstitutionFont(BaseFont.createFont("Font/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED));
                // 通过域名获取所在页和坐标，左下角为起点
                int pageno = form.getFieldPositions(fieldname).get(0).page;
                Rectangle signrect = form.getFieldPositions(fieldname).get(0).position;
                float x = signrect.getLeft();
                float y = signrect.getBottom();
                // 读图片
                Image image = Image.getInstance(imagepath);
                // 获取操作的页面
                PdfContentByte under = stamper.getOverContent(pageno);
                // 这里控制图片的大小
                //image.scaleToFit(signrect.getWidth(), signrect.getHeight());
                image.scaleToFit(60, 60);
                // 添加图片
                image.setAbsolutePosition(x-50, y-50);
                under.addImage(image);

                for (String key : acroFields.getFields().keySet()) {
                    acroFields.setFieldProperty(key, "textfont", base, null);
                    //字体大小
                    acroFields.setFieldProperty(key, "textsize", new Float(8), null);
                }
                if (fieldValueMap != null) {
                    for (String fieldName : fieldValueMap.keySet()) {
                        if (StringUtils.isNotBlank(fieldValueMap.get(fieldName))) {
                            //获取map中key对应的Value是否为On，若是则勾选复选框
                            if (fieldValueMap.get(fieldName).equals("On") || fieldValueMap.get(fieldName) == "On") {
                                acroFields.setField(fieldName, fieldValueMap.get(fieldName),"true");
                            }else{
                                acroFields.setField(fieldName, fieldValueMap.get(fieldName));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stamper != null) {
                    try {
                        stamper.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (Exception e) {
            System.out.println("填充参数异常");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    public static void main(String[] args) {
        //Map中Key对应PDF表单中的之前你设置的文本域名，Value则是你想填充的值
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
            genPdf(data,templatePdfPath,destPdfPath,imgURlMap);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
