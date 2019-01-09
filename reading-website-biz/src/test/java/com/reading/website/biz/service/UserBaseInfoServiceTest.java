package com.reading.website.biz.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoDTO;
import com.reading.website.api.service.UserBaseInfoService;
import com.reading.website.biz.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户基本信息服务测试类
 *
 * @yx8102 2019/1/7
 */
public class UserBaseInfoServiceTest extends BaseTest {

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @Test
    public void testInsertSelective() {
        UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
        userBaseInfoDO.setNickName("test006");
        userBaseInfoDO.setPassword("123456");
        userBaseInfoDO.setMobileNum("3452543");
        userBaseInfoDO.setWeiboName("杨兴哲");
        userBaseInfoDO.setWeChatId("test001");
        userBaseInfoDO.setEmail("1912246570@qq.com");
        userBaseInfoDO.setHeadPicPath("user/yxhiu/4793100/dsafd.jpg");
        userBaseInfoDO.setIsAdmin(false);
        BaseResult<Integer> res =  userBaseInfoService.insertSelective(userBaseInfoDO);
        System.out.println(res);
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
        userBaseInfoDO.setPassword("xnuikh");
        userBaseInfoDO.setId(1L);
        BaseResult<Integer> res =  userBaseInfoService.updateByPrimaryKeySelective(userBaseInfoDO);
        System.out.println(res);
    }

    @Test
    public void testSelectSelective() {
        UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
        //userBaseInfoDTO.setId(2L);
        //userBaseInfoDTO.setNickName("yx8102");
        //userBaseInfoDTO.setMobileNum("8978687689");
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        userBaseInfoDTO.setIds(ids);
        userBaseInfoDTO.setIsAdmin(true);
        BaseResult<List<UserBaseInfoDO>> res =  userBaseInfoService.selectSelective(userBaseInfoDTO);
        System.out.println(res);
    }
}
