package com.reading.website.biz.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BigCategoryDO;
import com.reading.website.api.domain.SmallCategoryDO;
import com.reading.website.api.service.CategoryService;
import com.reading.website.biz.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * CategoryServiceTest
 *
 * @xyang010 2019/1/16
 */
public class CategoryServiceTest extends BaseTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testGetAllCategory() {
        BaseResult<Map<BigCategoryDO, List<SmallCategoryDO>>> res = categoryService.listAllCategory();
        System.out.println(res);
    }
}
