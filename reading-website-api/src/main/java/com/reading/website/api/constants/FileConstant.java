package com.reading.website.api.constants;

/**
 * 文件相关常量类
 *
 * @xyang010 2019/3/8
 */
public interface FileConstant {
    // 上传统一路径
    String UPLOAD_PATH = "/Users/xyang010/Documents/IdeaProject/reading-website/reading-website-biz/src/main/resources/upload";

    // 图片子路径
    String PICTURE_SUB_PATH = "/picture";

    // 整本书子路径
    String BOOK_SUB_PATH = "/books";

    // 章节子路径
    String CHAPTER_SUB_PATH = "/chapters";

    // 图片类型
    String FILE_TYPE_JPG = "jpg";
    String FILE_TYPE_GIF = "gif";
    String FILE_TYPE_PNG = "png";
    String FILE_TYPE_BMP = "bmp";

    // 上传文件类型
    // 图书
    String BOOK = "book";

    // 章节
    String CHAPTER = "chapter";

    // 图片
    String PICTURE = "picture";

}
