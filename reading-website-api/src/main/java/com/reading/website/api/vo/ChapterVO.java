package com.reading.website.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * class comment
 *
 * @xyang010 2019/4/16
 */
@Data
public class ChapterVO {
    // 章节id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private Integer bookId;

    // 章节标题
    private String title;

    // 章节序号
    private Integer sequence;

    // 章节内容
    private String content;
}
