package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书实体类
 *
 * @yx8102 2019/1/21
 */
@ApiModel(value = "图书实体对象")
@Data
public class BookDO implements Serializable {

    // 图书id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

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

    // 扩展信息
    private String ext;
}