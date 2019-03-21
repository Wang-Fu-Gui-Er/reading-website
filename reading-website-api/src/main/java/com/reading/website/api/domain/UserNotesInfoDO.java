package com.reading.website.api.domain;

import lombok.Data;

import java.util.Date;

/**
 *  用户阅读笔记实体
 *
 * @yx8102  2019/3/17
 */
@Data
public class UserNotesInfoDO {
    // 笔记id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 用户id
    private Integer userId;

    // 图书id
    private Integer bookId;

    // 章节id
    private Integer chapId;

    // 原文内容
    private String content;

    // 批注内容
    private String notation;
}