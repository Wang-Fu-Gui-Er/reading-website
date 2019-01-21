package com.reading.website.biz.mapper;

import com.reading.website.api.domain.SmallCategoryDO;
import com.reading.website.biz.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * SmallCategoryMapperTest
 *
 * @xyang010 2019/1/16
 */
public class SmallCategoryMapperTest extends BaseTest {

    @Autowired
    private SmallCategoryMapper mapper;

    @Test
    public void testListByBigCateIds() {
        List<Long> bigCateIds = new ArrayList<>();
        bigCateIds.add(1L);
        List<SmallCategoryDO> res = mapper.listByBigCateIds(bigCateIds);
        System.out.println(res);
    }

    @Test
    public void test() {
        List<SmallCategoryDO> list = new ArrayList<>();

        list.add(new SmallCategoryDO("言情", 2L));
        list.add(new SmallCategoryDO("青春", 2L));
        list.add(new SmallCategoryDO("都市", 2L));
        list.add(new SmallCategoryDO("历史", 2L));
        list.add(new SmallCategoryDO("官场商战", 2L));
        list.add(new SmallCategoryDO("悬疑推理", 2L));
        list.add(new SmallCategoryDO("武侠魔幻", 2L));
        list.add(new SmallCategoryDO("社会乡土", 2L));
        list.add(new SmallCategoryDO("灵异恐怖", 2L));
        list.add(new SmallCategoryDO("影视文学", 2L));
        list.add(new SmallCategoryDO("现当代小说", 2L));
        list.add(new SmallCategoryDO("外国文学", 2L));
        list.add(new SmallCategoryDO("其他", 2L));
        list.forEach( bigCategoryDO -> {
            int res = mapper.insertSelective(bigCategoryDO);
            System.out.println(res);
        });
    }

    @Test
    public void testListHomeSmallCategory() {
        List<SmallCategoryDO> res = mapper.listHomeSmallCategory();
        System.out.println(res);
    }
}
