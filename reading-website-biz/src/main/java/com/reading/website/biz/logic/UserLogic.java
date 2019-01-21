package com.reading.website.biz.logic;

import com.reading.website.biz.mapper.UserBaseInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户逻辑类
 *
 * @xyang010 2019/1/9
 */
@Component
@Slf4j
public class UserLogic {
    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;
}
