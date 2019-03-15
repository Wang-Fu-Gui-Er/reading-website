package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookGradeInfoDO;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 图书评分信息测试类
 *
 * @xyang010 2019/3/15
 */
public class BookGradeInfoMapperTest extends BaseTest {

    @Autowired
    private BookGradeInfoMapper bookGradeInfoMapper;

    @Test
    public void test() {
        BookGradeInfoDO bookGradeInfoDO = new BookGradeInfoDO();
        bookGradeInfoDO.setBookId(1);
        bookGradeInfoDO.setReadCount(0);
        int res = bookGradeInfoMapper.insertOrUpdate(bookGradeInfoDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testSelectByBookId() {
        BookGradeInfoDO bookGradeInfoDO = bookGradeInfoMapper.selectByBookId(1);
        Assert.assertNotNull(bookGradeInfoDO);
    }
}
