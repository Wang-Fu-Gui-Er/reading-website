package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 图书实体类
 *
 * @yx8102 2019/1/21
 */
@ApiModel(value = "图书实体对象")
@Data
public class BookDO {
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private String bookName;

    private String bookPic;

    private Integer authorId;

    private String authorName;

    private Boolean isPublished;

    private Boolean isOver;

    private Integer smallCateId;

    private String bookPath;

    private String buyPath;

    private Date postedTime;

    private String bookDesc;

    private String ext;
}