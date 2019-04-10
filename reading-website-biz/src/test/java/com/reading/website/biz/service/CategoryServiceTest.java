package com.reading.website.biz.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BigCategoryDO;
import com.reading.website.api.service.CategoryService;
import com.reading.website.api.vo.CategoryVO;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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
        BaseResult<List<CategoryVO>> res = categoryService.listAllCategory();
        Assert.assertTrue(res.getSuccess());
        Assert.assertNotNull(res.getData());
        System.out.println(res);
    }
}
