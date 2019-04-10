package com.reading.website.api.vo;

import com.reading.website.api.domain.BigCategoryDO;
import com.reading.website.api.domain.SmallCategoryDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 图书分类VO
 *
 * @xyang010 2019/4/10
 */
@Data
public class CategoryVO implements Serializable {
    // 图书大类
    private BigCategoryDO bigCategoryDO;

    // 大类下包含的小类列表
    private List<SmallCategoryDO> smallCategoryList;
}
