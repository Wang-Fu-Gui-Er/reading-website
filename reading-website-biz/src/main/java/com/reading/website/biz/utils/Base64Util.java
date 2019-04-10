package com.reading.website.biz.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.reading.website.api.constants.FileConstant;
import sun.misc.BASE64Encoder;

/**
 * Base64编码解码工具
 *
 * @xyang010 2019/4/3
 */
public class Base64Util {

    /**
     * 本地文件转换成base64字符串
     * @param fileUrl	文件在服务器上的路径
     * @return
     */
    public static String fileToBase64ByLocal(String fileUrl) {


        InputStream inputStream = null;
        byte[] data = null;

        // 读取文件字节数组
        try {
            inputStream = new FileInputStream(fileUrl);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String fileEncoder = encoder.encode(data);

        // 获得文件类型
        String fileType = FileConstant.getFileType(fileUrl);

        // 文件类型转换为内容类型
        String fileContentType = FileConstant.convert2ContentType(fileType);
        if (fileContentType == null) {
            return null;
        }

        // 拼装base64内容
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:");
        stringBuilder.append(fileContentType);
        stringBuilder.append(";base64,");
        stringBuilder.append(fileEncoder);
        return stringBuilder.toString().replaceAll("\n", "").replaceAll("\r", "");
    }



    /**
     * 在线文件转换成base64字符串
     *
     * @param fileUrl	文件线上路径
     * @return
     */
    public static String fileToBase64ByOnline(String fileUrl) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(fileUrl);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String fileEncoder = encoder.encode(data.toByteArray());

        // 获得文件类型
        String fileType = FileConstant.getFileType(fileUrl);

        // 文件类型转换为内容类型
        String fileContentType = FileConstant.convert2ContentType(fileType);
        if (fileContentType == null) {
            return null;
        }

        // 拼装base64内容
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data:");
        stringBuilder.append(fileContentType);
        stringBuilder.append(";base64,");
        stringBuilder.append(fileEncoder);
        return stringBuilder.toString().replaceAll("\n", "");
    }
}
