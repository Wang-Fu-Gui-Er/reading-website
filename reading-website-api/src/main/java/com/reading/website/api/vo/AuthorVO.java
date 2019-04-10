package com.reading.website.api.vo;


import lombok.Data;

/**
 * 作者展示实体
 *
 * @xyang010 2019/4/8
 */
@Data
public class AuthorVO {
    // 作者id
    private Integer id;

    // 作者名称
    private String authorName;

    // 作者头像
    private String authorPic;

    // 作者微博昵称
    private String weiboName;

    // 作者简介
    private String authorDesc;

    // 代表作品
    private String represenWorks;
}
