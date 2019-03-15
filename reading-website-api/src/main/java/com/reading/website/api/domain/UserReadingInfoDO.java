package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 阅读记录信息
 *
 * @yx8102 2019/3/13
 */
@ApiModel(value = "阅读记录信息")
@Data
public class UserReadingInfoDO {
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 用户id
    private Integer userId;

    // 章节id
    private Integer chapId;

    // 是否在书架中
    private Boolean isOnShelf;

    // 图书id
    private Integer bookId;



}