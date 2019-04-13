package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoQuery;
import com.reading.website.api.service.UserBaseInfoService;
import com.reading.website.biz.logic.UserLogic;
import com.reading.website.biz.mapper.UserBaseInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户基本服务实现类
 *
 * @yx8102 2019/1/7
 */
@Service
@Slf4j
public class UserBaseInfoServiceImpl implements UserBaseInfoService {

    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper;

    @Autowired
    private UserLogic userLogic;

    @Override
    public BaseResult<Integer> insertSelective(UserBaseInfoDO record) {
        if (null == record) {
            log.warn("UserBaseInfoService insertSelective param user is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.insertSelective(record));
        } catch (Exception e) {
            log.error("UserBaseInfoService insertSelective error {}", e);
            return BaseResult.errorReturn(null, StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    @Override
    public BaseResult<Integer> updateByEmailSelective(UserBaseInfoDO record) {
        if (null == record) {
            log.warn("UserBaseInfoService updateByEmailSelective param user is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.updateByEmailSelective(record));
        } catch (Exception e) {
            log.error("UserBaseInfoService updateByEmailSelective error {}", e);
            return BaseResult.errorReturn(null, StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据id修改用户信息
     * @param record
     * @return
     */
    @Override
    public BaseResult<Integer> updateByIdSelective(UserBaseInfoDO record) {
        if (null == record) {
            log.warn("UserBaseInfoService updateByIdSelective param user is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.updateByIdSelective(record));
        } catch (Exception e) {
            log.error("UserBaseInfoService updateByIdSelective error {}", e);
            return BaseResult.errorReturn(null, StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    @Override
    public BaseResult<List<UserBaseInfoDO>> selectSelective(UserBaseInfoQuery query) {
        if (null == query) {
            log.warn("UserBaseInfoService selectSelective param user is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.selectSelective(query));
        } catch (Exception e) {
            log.error("UserBaseInfoService selectSelective error {}", e);
            return BaseResult.errorReturn(null, StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据邮箱地址查询
     * @param email
     * @return
     */
    @Override
    public BaseResult<UserBaseInfoDO> selectByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            log.warn("UserBaseInfoService selectByEmail param email is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param email is null");
        }
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        query.setEmail(email);
        BaseResult<List<UserBaseInfoDO>> queryRes = selectSelective(query);
        if (!queryRes.getSuccess()) {
            log.warn("UserBaseInfoService selectByEmail failed");
            return BaseResult.errorReturn(null, StatusCodeEnum.INNER_SERVICE_ERROR.getCode(), "查询失败");
        }

        if (!CollectionUtils.isEmpty(queryRes.getData())) {
            return BaseResult.rightReturn(queryRes.getData().get(0));
        }
        return BaseResult.rightReturn(null);
    }

    /**
     * 根据id查询
     * @param id 用户id
     * @return
     */
    @Override
    public BaseResult<UserBaseInfoDO> selectById(Integer id) {
        if (id == null) {
            log.warn("UserBaseInfoService selectById param id is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param id is null");
        }
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        query.setId(id);
        BaseResult<List<UserBaseInfoDO>> queryRes = selectSelective(query);
        if (!queryRes.getSuccess()) {
            log.warn("UserBaseInfoService selectById failed");
            return BaseResult.errorReturn(null, StatusCodeEnum.INNER_SERVICE_ERROR.getCode(), "查询失败");
        }

        if (!CollectionUtils.isEmpty(queryRes.getData())) {
            return BaseResult.rightReturn(queryRes.getData().get(0));
        }
        return BaseResult.rightReturn(null);
    }
}
