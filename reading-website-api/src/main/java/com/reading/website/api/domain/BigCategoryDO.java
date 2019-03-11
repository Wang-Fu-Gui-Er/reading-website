package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    // 打类下的小类集合
    private List<SmallCategoryDO> smallCategoryList;

    public BigCategoryDO() {
    }

    public BigCategoryDO(String cateName) {
        this.cateName = cateName;
    }
}