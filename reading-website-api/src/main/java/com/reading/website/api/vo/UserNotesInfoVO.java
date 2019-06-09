package com.reading.website.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户笔记
 *
 * @xyang010 2019/6/9
 */
@Data
public class UserNotesInfoVO implements Serializable {

    // 笔记id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 用户id
    private Integer userId;

    // 图书id
    private Integer bookId;

    // 图书名称
    private String bookName;

    // 图书封面
    private String bookPic;

    // 章节id
    private Integer chapId;

    // 原文内容
    private String content;

    // 批注内容
    private String notation;
}
