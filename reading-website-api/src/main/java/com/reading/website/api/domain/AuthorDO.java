package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 作者信息
 *
 * @yx8102 2019/3/13
 */
@ApiModel(value = "作者信息")
@Data
public class AuthorDO {

    // 作者id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 作者名称
    private String authorName;

    // 作者头像地址
    private String authorPic;

    // 作者微博昵称
    private String weiboName;

    // 作者简介
    private String authorDesc;

    // 代表作品
    private String represenWorks;
}