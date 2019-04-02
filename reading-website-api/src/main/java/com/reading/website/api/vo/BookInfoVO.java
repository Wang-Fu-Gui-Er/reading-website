package com.reading.website.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * 图书VO
 *
 * @xyang010 2019/4/1
 */
@Data
public class BookInfoVO {

    // 图书id
    private Integer id;

    // 图书名称
    private String bookName;

    // 图书封面地址
    private String bookPic;

    // 作者id
    private Integer authorId;

    // 作者名称
    private String authorName;

    // 是否出版
    private Boolean isPublished;

    // 是否完结
    private Boolean isOver;

    // 小类id
    private Integer smallCateId;

    // 图书地址
    private String bookPath;

    // 购买地址
    private String buyPath;

    // 发表时间
    private Date postedTime;

    // 图书简介
    private String bookDesc;

    // 章节数
    private Integer chapNum;

    // 平均分
    private Integer avgScore;

}
