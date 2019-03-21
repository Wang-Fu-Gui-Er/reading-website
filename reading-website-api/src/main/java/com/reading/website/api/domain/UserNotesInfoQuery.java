package com.reading.website.api.domain;

import com.reading.website.api.base.Page;
import lombok.Data;

/**
 * 阅读笔记查询实体
 *
 * @xyang010 2019/3/17
 */
@Data
public class UserNotesInfoQuery extends Page {
    // 笔记id
    private Integer id;

    // 用户id
    private Integer userId;

    // 图书id
    private Integer bookId;

    // 章节id
    private Integer chapId;

}
