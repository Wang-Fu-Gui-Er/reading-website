package com.reading.website.api.service;


import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户基本信息服务类
 *
 * @yx8102 2019/1/7
 */
@Service
public interface UserBaseInfoService {
    /**
     * 新增用户信息
     * @param record
     * @return
     */
    BaseResult<Integer> insertSelective(UserBaseInfoDO record);

    /**
     * 根据邮箱地址修改用户信息
     * @param record
     * @return
     */
    BaseResult<Integer> updateByEmailSelective(UserBaseInfoDO record);

    /**
     * 根据id修改用户信息
     * @param record
     * @return
     */
    BaseResult<Integer> updateByIdSelective(UserBaseInfoDO record);

    /**
     * 条件查询
     * @param query
     * @return
     */
    BaseResult<List<UserBaseInfoDO>> selectSelective(UserBaseInfoQuery query);

    /**
     * 根据id查询
     * @param email
     * @return
     */
    BaseResult<UserBaseInfoDO> selectByEmail(String email);

    /**
     * 根据邮箱地址查询
     * @param id 用户id
     * @return
     */
    BaseResult<UserBaseInfoDO> selectById(Long id);
}
