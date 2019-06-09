package com.reading.website.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书评论VO
 *
 * @xyang010 2019/4/13
 */
@Data
public class BookReviewVO implements Serializable {
    private Integer id;

    private Date created;

    private Date updated;

    // 图书id
    private Integer bookId;

    // 图书封面
    private String bookPic;

    // 用户id
    private Integer userId;

    // 用户昵称
    private String nickName;

    // 用户头像
    private String headPicPath;

    // 评论内容
    private String comment;

    // 点赞数
    private Integer likeNum;

    // 用户评分
    private Integer grade = 0;

    // 是否已点赞
    private Boolean isLike = Boolean.FALSE;
}
