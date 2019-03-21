package com.reading.website.biz.mapper;

import com.reading.website.api.domain.AdviceInfoDO;
import com.reading.website.api.domain.AdviceInfoQuery;
import com.reading.website.api.enums.AdviceHandleStatusEnum;
import com.reading.website.api.enums.AdviceTypeEnum;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AdviceInfoMapperTest
 *
 * @xyang010 2019/3/21
 */
public class AdviceInfoMapperTest extends BaseTest {

    @Autowired
    private AdviceInfoMapper adviceInfoMapper;

    @Test
    public void testInsertSelective() {
        AdviceInfoDO adviceInfoDO = new AdviceInfoDO();
        adviceInfoDO.setStatus(AdviceHandleStatusEnum.PENDING.getCode());
        adviceInfoDO.setUserId(26);
        adviceInfoDO.setType(AdviceTypeEnum.PROPOSAL.getCode());
        adviceInfoDO.setTitle("优化建议");
        adviceInfoDO.setDetail("我有一个优化建议");
        adviceInfoDO.setEmail("1912246570@qq.com");
        int res = adviceInfoMapper.insertSelective(adviceInfoDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        AdviceInfoDO adviceInfoDO = new AdviceInfoDO();
        adviceInfoDO.setId(1);
        adviceInfoDO.setStatus(AdviceHandleStatusEnum.PROCESSING.getCode());
        int res = adviceInfoMapper.updateByPrimaryKeySelective(adviceInfoDO);
        Assert.assertTrue(res > 0);
    }

    @Test
    public void testPageQuery() {
        AdviceInfoQuery query = new AdviceInfoQuery();
        query.setStatus(AdviceHandleStatusEnum.PROCESSING.getCode());
        query.setUserId(26);
        List<AdviceInfoDO> list = adviceInfoMapper.pageQuery(query);
        int count = adviceInfoMapper.count(query);
        Assert.assertNotNull(list);
        Assert.assertTrue(count > 0);
    }



}
