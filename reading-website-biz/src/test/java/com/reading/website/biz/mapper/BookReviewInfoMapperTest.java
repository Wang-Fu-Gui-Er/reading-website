package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * class comment
 *
 * @xyang010 2019/3/17
 */
public class BookReviewInfoMapperTest extends BaseTest {

    @Autowired
    private BookReviewInfoMapper mapper;

    @Test
    public void testInsertOrUpdate() {
        BookReviewInfoDO reviewInfoDO = new BookReviewInfoDO();
        reviewInfoDO.setBookId(1);
        reviewInfoDO.setUserId(26);
        reviewInfoDO.setComment("垃圾");
        reviewInfoDO.setLikeNum(0);
        int res = mapper.insertOrUpdate(reviewInfoDO);
        Assert.assertTrue(res > 0);

    }

    @Test
    public void testPageQuery() {
        BookReviewInfoQuery query = new BookReviewInfoQuery();
        query.setUserId(26);
        query.setPageNum(2);
        List<BookReviewInfoDO> list = mapper.pageQuery(query);
        int count = mapper.count(query);
        Assert.assertNotNull(list);
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testUpdateLikeNum() {
        int res = mapper.updateLikeNum(1, true);
        System.out.println(res);
    }
}
