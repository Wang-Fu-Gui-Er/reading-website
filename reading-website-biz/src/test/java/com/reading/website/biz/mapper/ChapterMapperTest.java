package com.reading.website.biz.mapper;

import com.reading.website.api.domain.ChapterDO;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * ChapterMapperTest
 *
 * @xyang010 2019/3/13
 */
public class ChapterMapperTest extends BaseTest {

    @Autowired
    private ChapterMapper chapterMapper;

    @Test
    public void testBatchInsert() {
        List<ChapterDO> list = new ArrayList<>();

        ChapterDO chapterDO = new ChapterDO();
        chapterDO.setBookId(1);
        chapterDO.setSequence(1);
        chapterDO.setTitle("title");
        chapterDO.setContentPath("path");

        ChapterDO chapterDO2 = new ChapterDO();
        chapterDO2.setBookId(1);
        chapterDO2.setSequence(2);
        chapterDO2.setTitle("title");
        chapterDO2.setContentPath("path");

        list.add(chapterDO);
        list.add(chapterDO2);
        int res = chapterMapper.batchInsert(list);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testBatchUpdate() {
        List<ChapterDO> list = new ArrayList<>();
        ChapterDO chapterDO = new ChapterDO();
        chapterDO.setId(1);
        chapterDO.setBookId(1);
        chapterDO.setSequence(1);
        chapterDO.setTitle("");
        chapterDO.setContentPath("");

        ChapterDO chapterDO2 = new ChapterDO();
        chapterDO2.setId(2);
        chapterDO2.setBookId(1);
        chapterDO2.setSequence(2);
        chapterDO2.setTitle("");
        chapterDO2.setContentPath("");

        list.add(chapterDO);
        list.add(chapterDO2);
        int res = chapterMapper.batchUpdate(list);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testSelectByBookId() {
        List<ChapterDO> list = chapterMapper.selectByBookId(1, "asc");
        Assert.assertNotNull(list);
    }
}
