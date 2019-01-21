package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 图书分类，大类
 *
 * @yx8102 2019/1/16
 */
@ApiModel(value="图书分类，大类对象")
@Data
public class BigCategoryDO {
    /**
     * 大类id
     */
    private Long id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    /**
     * 大类名称
     */
    private String cateName;

    public BigCategoryDO() {
    }

    public BigCategoryDO(String cateName) {
        this.cateName = cateName;
    }
}