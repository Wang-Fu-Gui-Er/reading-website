package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 图书评价信息
 *
 * @yx8102 2019/3/13
 */
@ApiModel(value = "图书评价信息")
@Data
public class BookReviewInfoDO implements Serializable {
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 图书id
    private Integer bookId;

    // 用户id
    private Integer userId;

    // 评论内容
    private String comment;

    // 点赞数
    private Integer likeNum;
}