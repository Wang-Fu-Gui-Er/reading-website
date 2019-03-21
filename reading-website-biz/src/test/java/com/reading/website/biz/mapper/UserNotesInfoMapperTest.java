package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserNotesInfoDO;
import com.reading.website.api.domain.UserNotesInfoQuery;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * UserNotesInfoMapperTest
 *
 * @xyang010 2019/3/18
 */
public class UserNotesInfoMapperTest extends BaseTest {

    @Autowired
    private UserNotesInfoMapper notesInfoMapper;

    @Test
    public void testInsertSelective() {
        UserNotesInfoDO notesInfoDO = new UserNotesInfoDO();
        notesInfoDO.setBookId(1);
        notesInfoDO.setChapId(1);
        notesInfoDO.setUserId(26);
        notesInfoDO.setContent("原文片段");
        notesInfoDO.setNotation("棒棒");
        int res = notesInfoMapper.insertSelective(notesInfoDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        UserNotesInfoDO notesInfoDO = new UserNotesInfoDO();
        notesInfoDO.setId(1);
        notesInfoDO.setNotation("棒棒哒～");
        int res = notesInfoMapper.updateByPrimaryKeySelective(notesInfoDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testPageQuery() {
        UserNotesInfoQuery query = new UserNotesInfoQuery();
        query.setUserId(26);
        List<UserNotesInfoDO> list = notesInfoMapper.pageQuery(query);
        int count = notesInfoMapper.countPageQuery(query);
        Assert.assertNotNull(list);
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testSelectBookIdByUserId() {
        List<UserNotesInfoDO> bookIds = notesInfoMapper.selectByUserIdGroupBookId(26, 0, 10);
        int count = notesInfoMapper.countSelectByUserIdGroupBookId(26);
        Assert.assertNotNull(bookIds);
        Assert.assertTrue(count > 0);
    }

}
