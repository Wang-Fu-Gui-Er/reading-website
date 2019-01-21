package com.reading.website.biz.mapper;

import com.alibaba.fastjson.JSON;
import com.reading.website.api.domain.BigCategoryDO;
import com.reading.website.biz.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * BigCategoryMapperTest
 *
 * @xyang010 2019/1/16
 */
public class BigCategoryMapperTest extends BaseTest {

    @Autowired
    private BigCategoryMapper mapper;

    @Test
    public void testListAllBigCategory() {
        List<BigCategoryDO> res = mapper.listAll();
        System.out.println(JSON.toJSON(res));
    }

    @Test
    public void testInsertSelective() {
        List<BigCategoryDO> list = new ArrayList<>();
        list.add(new BigCategoryDO("小说"));
        list.add(new BigCategoryDO("经济管理"));
        list.add(new BigCategoryDO("历史传记"));
        list.add(new BigCategoryDO("计算机"));
        list.add(new BigCategoryDO("文学艺术"));
        list.add(new BigCategoryDO("社会科学"));
        list.add(new BigCategoryDO("科技"));
        list.add(new BigCategoryDO("生活"));
        list.forEach( bigCategoryDO -> {
            int res = mapper.insertSelective(bigCategoryDO);
            System.out.println(res);
        });

    }
}
