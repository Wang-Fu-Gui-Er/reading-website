package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserGradeInfoDO;

/**
 * 用户评分服务
 *
 * @xyang010 2019/6/8
 */
public interface UserGradeInfoService {

    /**
     * 新增或更新
     * @param userGradeInfoDO
     * @return
     */
    BaseResult<Integer> insertOrUpdate(UserGradeInfoDO userGradeInfoDO);

    /**
     * 根据用户id查询
     * @param userId 用户id
     * @param bookId 图书id
     * @return
     */
    BaseResult<UserGradeInfoDO> selectByUserIdAndBookId(Integer userId, Integer bookId);
}
