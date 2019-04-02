package com.reading.website.api.constants;

import org.springframework.util.StringUtils;

/**
 * 文件相关常量类
 *
 * @xyang010 2019/3/8
 */
public class FileConstant {
    // 上传统一路径
    public static final String UPLOAD_PATH = "/Users/xyang010/Documents/IdeaProject/reading-website/reading-website-biz/src/main/resources/upload";

    // 图片子路径
    public static final String PICTURE_SUB_PATH = "/picture";

    // 整本书子路径
    public static final String BOOK_SUB_PATH = "/books";

    // 章节子路径
    public static final String CHAPTER_SUB_PATH = "/chapters";

    // 图片类型
    public static final String FILE_TYPE_JPG = "jpg";
    public static final String FILE_TYPE_GIF = "gif";
    public static final String FILE_TYPE_PNG = "png";
    public static final String FILE_TYPE_BMP = "bmp";

    // 上传文件类型
    // 图书
    public static final String BOOK = "book";

    // 章节
    public static final String CHAPTER = "chapter";

    // 图片
    public static final String PICTURE = "picture";

    /**
     * 获取文件类型
     * @param path 文件全路径
     * @return 文件类型
     */
    public static String getFileType(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    /**
     * 转换文件类型为后缀
     * @param fileContentType
     * @return
     */
    public static String convertFileType(String fileContentType) {
        if (StringUtils.isEmpty(fileContentType)) {
            return null;
        }

        switch (fileContentType) {
            case "image/jpeg": return "jpeg";
            case "image/gif": return "gif";
            case "image/x-ms-bmp": return "bmp";
            case "image/png": return "png";
            case "text/plain": return "txt";
            case "application/pdf": return "pdf";
            default: return null;
        }
    }
}
