package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BookMapperTest
 *
 * @xyang010 2019/3/11
 */
public class BookMapperTest extends BaseTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    public void testSelectSelective() {
        BookInfoQuery query = new BookInfoQuery();
        query.setAuthorId(1);
        List<BookDO> list =  bookMapper.selectSelective(query);
        Assert.assertNotNull(list);
    }

    @Test
    public void testPageQuery() {
        BookInfoQuery query = new BookInfoQuery();
        query.setPageNum(2);
        query.setPageSize(2);
        List<BookDO> list = bookMapper.pageQuery(query);
        Assert.assertNotNull(list);
    }

    @Test
    public void testInsertOrUpdate() {
        BookDO bookDO = new BookDO();
        bookDO.setId(52);
        bookDO.setBookName("兄弟");
        bookDO.setAuthorId(1);
        bookDO.setAuthorName("余华");
        bookDO.setSmallCateId(12);
        bookDO.setIsPublished(true);
        bookDO.setIsOver(true);
        bookDO.setPostedTime(Date.from(LocalDate.of(2012, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookDO.setBookDesc("        本书讲述了江南小镇两兄弟李光头和宋钢的人生。李光头的父亲不怎么光彩地意外身亡，而同一天李光头出生。宋钢的父亲宋凡平在众人的嘲笑声中挺身而出，帮助了李光头的母亲李兰，被后者视为恩人。几年后宋钢的母亲也亡故，李兰和宋凡平在互相帮助中相爱并结婚。虽然这场婚姻遭到了镇上人们的鄙夷和嘲弄，但两人依然相爱甚笃，而李光头和宋钢这对没有血缘关系的兄弟也十分投缘，他们在相互照顾中成长……");
        int res = bookMapper.insertOrUpdate(bookDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testBatchInsert() {
        List<BookDO> list = new ArrayList<>();
        BookDO bookDO = new BookDO();
        bookDO.setBookName("许三观卖血记");
        bookDO.setAuthorId(1);
        bookDO.setAuthorName("余华");
        bookDO.setSmallCateId(12);
        bookDO.setIsPublished(true);
        bookDO.setIsOver(true);
        bookDO.setPostedTime(Date.from(LocalDate.of(2012, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookDO.setBookDesc("本书以博大的温情描绘了磨难中的人生，以激烈的故事形式表达了人在面对厄运时求生的欲望。小说讲述了许三观靠着卖血渡过了人生的一个个难关，战胜了命运强加给他的惊涛骇浪，而当他老了，知道自己的血再也没有人要时，他哭了。法国《读书》杂志在评论《许三观卖血记》时说道：这是一部精妙绝伦的小说，是朴实简洁和内涵意蕴深远的完美结合。本书入选韩国《中央日报》评选的“100部必读书”（2000年）、中国百位批评家和文学编辑评选的“20世纪90年代最有影响的10部作品”。");
        BookDO bookDO1 = new BookDO();
        bookDO1.setBookName("在细雨中呼喊");
        bookDO1.setAuthorId(1);
        bookDO1.setAuthorName("余华");
        bookDO1.setSmallCateId(12);
        bookDO1.setIsPublished(true);
        bookDO1.setIsOver(true);
        bookDO1.setPostedTime(Date.from(LocalDate.of(2012, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        bookDO1.setBookDesc("本书描述了一位江南少年的成长经历和心灵历程。作品的结构来自于对时间的感受，确切地说是对记忆中的时间的感受，叙述者天马行空地在过去、现在和将来这三个时间维度里自由穿行，将忆记的碎片穿插、结集、拼嵌完整。作者因本书荣获法国文学和艺术骑士勋章等奖。");
        list.add(bookDO);
        list.add(bookDO1);
        int res = bookMapper.batchInsert(list);
        Assert.assertTrue(res > 0);
    }
}
