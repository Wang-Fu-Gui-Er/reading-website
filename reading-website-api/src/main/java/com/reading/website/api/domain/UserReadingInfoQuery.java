package com.reading.website.api.domain;

import com.reading.website.api.base.Page;
import lombok.Data;


/**
 * 用户阅读信息查询条件
 *
 * @xyang010 2019/3/13
 */
@Data
public class UserReadingInfoQuery extends Page {
    private Integer id;

    // 用户id
    private Integer userId;

    // 章节id
    private Integer chapId;

    // 是否在书架中
    private Boolean isOnShelf;

    // 图书id
    private Integer bookId;

}
