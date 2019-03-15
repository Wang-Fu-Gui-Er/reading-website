package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;

import java.util.List;

/**
 * 用户阅读记录服务
 *
 * @xyang010 2019/3/13
 */
public interface UserReadingService {

    /**
     * 增加或更新
     * @param userReadingInfoDO 阅读记录实体
     * @return
     */
    BaseResult<Integer> insertOrUpdate(UserReadingInfoDO userReadingInfoDO);


    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    BaseResult<List<UserReadingInfoDO>> pageQuery(UserReadingInfoQuery query);
}
