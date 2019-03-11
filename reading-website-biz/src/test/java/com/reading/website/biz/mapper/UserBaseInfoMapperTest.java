package com.reading.website.biz.mapper;


import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoQuery;
import com.reading.website.biz.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户基本信息Mapper测试类
 *
 * @yx8102 2019/1/7
 */
public class UserBaseInfoMapperTest extends BaseTest {
    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

    @Test
    public void testInsertSelective() {
        UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
        userBaseInfoDO.setNickName("test005");
        userBaseInfoDO.setPassword("123456");
        userBaseInfoDO.setMobileNum("4523545");
        userBaseInfoDO.setWeiboName("杨兴哲");
        userBaseInfoDO.setWeChatId("test001");
        userBaseInfoDO.setEmail("1912246570@qq.com");
        userBaseInfoDO.setHeadPicPath("user/yxhiu/4793100/dsafd.jpg");
        userBaseInfoDO.setIsAdmin(false);
        int res = userBaseInfoMapper.insertSelective(userBaseInfoDO);
        System.out.println(res);
    }

    @Test
    public void testUpdateByEmailSelective() {
        UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
        userBaseInfoDO.setId(1L);
        userBaseInfoDO.setIsAdmin(true);
        int res = userBaseInfoMapper.updateByEmailSelective(userBaseInfoDO);
        System.out.println(res);
    }

    @Test
    public void testSelectSelective() {
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        //userBaseInfoDTO.setId(2L);
        //userBaseInfoDTO.setNickName("yx8102");
        //userBaseInfoDTO.setMobileNum("8978687689");
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        query.setIds(ids);
        List<UserBaseInfoDO> res = userBaseInfoMapper.selectSelective(query);
        System.out.println(res);
    }
}
