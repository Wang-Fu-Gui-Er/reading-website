package com.reading.website.api.domain;

import com.reading.website.api.base.Page;
import lombok.Data;

import java.util.Date;

/**
 * 图书查询类
 *
 * @xyang010 2019/2/25
 */
@Data
public class BookInfoQuery extends Page {

    // 图书id
    private Integer id;

    // 图书名称
    private String bookName;

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

    // 发表时间
    private Date postedTime;
}
