package com.reading.website.api.domain;

import com.reading.website.api.base.Page;
import lombok.Data;

/**
 * 图书评论查询实体
 *
 * @xyang010 2019/3/15
 */
@Data
public class BookReviewInfoQuery extends Page {

    // 评论id
    private Integer id;

    // 图书id
    private Integer bookId;

    // 用户id
    private Integer userId;

    // 评论排序方式
    private String sort;

}
