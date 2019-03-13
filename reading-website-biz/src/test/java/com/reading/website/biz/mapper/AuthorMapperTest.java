package com.reading.website.biz.mapper;

import com.reading.website.api.domain.AuthorDO;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AuthorMapperTest
 *
 * @xyang010 2019/3/13
 */
public class AuthorMapperTest extends BaseTest {
    @Autowired
    private AuthorMapper authorMapper;

    @Test
    public void testInsertOrUpdate() {
        AuthorDO authorDO = new AuthorDO();
        authorDO.setId(1);
        authorDO.setAuthorName("余华");
        authorDO.setAuthorDesc("1960年4月3日生于浙江杭州，当代作家。中国作家协会第九届全国委员会委员。 1977年中学毕业后，进入北京鲁迅文学院进修深造。1983年开始创作，同年进入浙江省海盐县文化馆。1984年开始发表小说，《活着》和《许三观卖血记》同时入选百位批评家和文学编辑评选的九十年代最具有影响的十部作品。1998年获意大利格林扎纳·卡佛文学奖。2005年获得中华图书特殊贡献奖。现就职于杭州文联。");
        authorDO.setRepresenWorks("活着、许三观卖血记、十八岁出门远行、鲜血梅花、世事如烟、兄弟、在细雨中呼喊、现实一种、战栗");
        int res = authorMapper.insertOrUpdate(authorDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testSelectByPrimaryKey() {
        AuthorDO authorDO = authorMapper.selectByPrimaryKey(1);
        Assert.assertNotNull(authorDO);
    }

    @Test
    public void testFuzzySelectByAuthorName() {
        List<AuthorDO> list = authorMapper.fuzzySelectByAuthorName("华");
        Assert.assertNotNull(list);
    }
}
