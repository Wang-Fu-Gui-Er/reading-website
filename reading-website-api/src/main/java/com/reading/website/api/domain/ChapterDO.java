package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 章节信息
 *
 * @yx8102 2019/3/13
 */
@ApiModel(value = "章节信息")
@Data
public class ChapterDO implements Serializable {

    // 章节id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    private Integer bookId;

    // 章节标题
    private String title;

    // 章节序号
    private Integer sequence;

    // 章节路径
    private String contentPath;
}