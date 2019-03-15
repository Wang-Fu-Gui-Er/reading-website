package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * UserReadingInfoMapperTest
 *
 * @xyang010 2019/3/13
 */
public class UserReadingInfoMapperTest extends BaseTest {

    @Autowired
    private UserReadingInfoMapper readingInfoMapper;

    @Test
    public void testInsertOrUpdate() {
        UserReadingInfoDO userReadingInfoDO = new UserReadingInfoDO();
        userReadingInfoDO.setUserId(1);
        userReadingInfoDO.setBookId(1);
        userReadingInfoDO.setChapId(2);
        userReadingInfoDO.setIsOnShelf(true);
        int res = readingInfoMapper.insertOrUpdate(userReadingInfoDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testPageQuery() {
        UserReadingInfoQuery query = new UserReadingInfoQuery();
        query.setUserId(1);
//        query.setIsOnShelf(false);
        List<UserReadingInfoDO> list = readingInfoMapper.pageQuery(query);
        Assert.assertNotNull(list);
    }


}
