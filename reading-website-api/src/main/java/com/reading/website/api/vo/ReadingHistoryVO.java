package com.reading.website.api.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 阅读历史展示实体
 *
 * @xyang010 2019/3/13
 */
@Data
public class ReadingHistoryVO implements Serializable {

    // 图书id
    private Integer bookId;

    // 图书名称
    private String bookName;

    // 图书封面地址
    private String bookPic;

    // 图书地址
    private String bookPath;

    // 作者id
    private Integer authorId;

    // 作者名称
    private String authorName;

    // 章节id
    private Integer chapId;

    // 章节标题
    private String title;

    // 章节序号
    private Integer sequence;

    // 章节路径
    private String contentPath;

    // 阅读进度
    private Integer progress;
}
