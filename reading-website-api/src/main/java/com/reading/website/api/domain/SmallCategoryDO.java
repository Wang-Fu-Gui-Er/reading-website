package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 图书分类，小类
 *
 * @yx8102 2019/1/16
 */
@ApiModel(value="图书分类，小类对象")
@Data
public class SmallCategoryDO {
    /**
     * 小类id
     */
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    /**
     * 小类名称
     */
    private String cateName;

    /**
     * 对应大类id
     */
    private Integer bigCateId;

    /**
     * 是否在首页展示
     */
    private Boolean isHome;

    public SmallCategoryDO() {
    }

    public SmallCategoryDO(String cateName, Integer bigCateId) {
        this.cateName = cateName;
        this.bigCateId = bigCateId;
    }
}