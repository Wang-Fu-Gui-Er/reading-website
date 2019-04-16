package com.reading.website.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 图书分类DTO
 *
 * @xyang010 2019/4/16
 */
@Data
public class CategoryDTO implements Serializable {

    // 大类id
    private Integer bigCateId;

    // 大类名称
    private String bigCateName;

    // 小类id
    private Integer smallCateId;

    // 小类名称
    private String smallCateName;

}
