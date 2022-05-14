package com.wenqiao.utils;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WordUtils {
    /**
     * 填充word模板
     */
    public static void wordOperate(){
        Map<String, Object> map = new HashMap<>();
        map.put("author", "张三");
        map.put("date", "2022年5月14日");
        // 读取本地磁盘图片
        String path = WordUtils.class.getResource("/static/sign.png").getPath();
        map.put("picture", new PictureRenderData(100, 100, path));
        // 通过url读取网络图片
//        map.put("picture", new PictureRenderData(200, 400, ".png", BytePictureUtils.getUrlByteArray("https://res.wx.qq.com/a/wx_fed/weixin_portal/res/static/img/1EtCRvm.png")));
        InputStream inputStream = null;
        XWPFTemplate template = null;
        FileOutputStream out = null;
        try {
            inputStream = WordUtils.class.getResourceAsStream("/templates/mytemplate.docx");
            template = XWPFTemplate.compile(inputStream).render(map);
            out = new FileOutputStream("D:\\word\\aftermytemplate.docx");
            template.write(out);
        } catch (IOException e) {
            log.error("wordOperate error:", e);
        } finally {
            try {
                template.close();
                out.close();
                inputStream.close();
            } catch (IOException e) {
                log.error("close stream error", e);
            }
        }

    }

    /**
     * word转pdf
     */
    public static void wordToPDF(){
        String filepath = "D:\\word\\aftermytemplate.docx";
        String outpath = "D:\\word\\aftermytemplate.pdf";

        InputStream source;
        OutputStream target;
        try {
            source = new FileInputStream(filepath);
            target = new FileOutputStream(outpath);

            PdfOptions options = PdfOptions.create();
            XWPFDocument docx = new XWPFDocument(source);
            PdfConverter.getInstance().convert(docx, target, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
           log.error("wordtopdf error:", e);
        }
    }

}
