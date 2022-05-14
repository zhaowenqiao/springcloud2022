package com.wenqiao.utils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.ConnectException;

/**
 * 在C:\Program Files (x86)\OpenOffice 4\program目录下启动openoffice命令:soffice.exe -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
 *
 */
@Slf4j
public class OpenOfficeUtils {

    public static void wordTopdf(){
        // 源文件目录
        File inputFile = new File("D:\\word\\aftermytemplate.doc");
        if (!inputFile.exists()) {
            System.out.println("源文件不存在！");
            return;
        }
        File outputFile = new File("D:\\word\\aftermytemplate2.pdf");
        // 输出文件目录
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().exists();
        }
        // 调用openoffice服务线程
        //String command = "D:\\tools\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100\"";
        //Process p = Runtime.getRuntime().exec(command);

        // 连接openoffice服务
        OpenOfficeConnection connection = null;
        try {
            connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();

            // 转换word到pdf
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
        } catch (ConnectException e) {
            log.error("error is:", e);
        } finally {
            // 关闭连接
            if(connection != null){
                connection.disconnect();
            }
        }

        // 关闭进程
        //p.destroy();
        System.out.println("转换完成！");
    }

    public static void main(String[] args) {
        OpenOfficeUtils.wordTopdf();
    }

}
