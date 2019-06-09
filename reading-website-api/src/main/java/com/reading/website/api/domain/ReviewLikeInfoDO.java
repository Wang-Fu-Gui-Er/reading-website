package com.reading.website.api.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewLikeInfoDO {
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 评论id
    private Integer reviewId;

    // 点赞用户id
    private Integer userId;
}