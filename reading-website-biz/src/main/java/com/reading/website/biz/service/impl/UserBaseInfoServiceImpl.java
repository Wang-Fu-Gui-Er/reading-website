package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoDTO;
import com.reading.website.api.service.UserBaseInfoService;
import com.reading.website.biz.constants.ServiceCodeConstant;
import com.reading.website.biz.mapper.UserBaseInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public BaseResult<Integer> insertSelective(UserBaseInfoDO record) {
        if (null == record) {
            log.warn("UserBaseInfoService insertSelective param user is null");
            return BaseResult.errorReturn(null, ServiceCodeConstant.PARAM_ERROR, "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.insertSelective(record));
        } catch (Exception e) {
            log.error("UserBaseInfoService insertSelective error {}", e);
            return BaseResult.errorReturn(null, ServiceCodeConstant.MAPPER_ERROR, "mapper error");
        }
    }

    @Override
    public BaseResult<Integer> updateByPrimaryKeySelective(UserBaseInfoDO record) {
        if (null == record) {
            log.warn("UserBaseInfoService insertSelective param user is null");
            return BaseResult.errorReturn(null, ServiceCodeConstant.PARAM_ERROR, "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.updateByPrimaryKeySelective(record));
        } catch (Exception e) {
            log.error("UserBaseInfoService updateByPrimaryKeySelective error {}", e);
            return BaseResult.errorReturn(null, ServiceCodeConstant.MAPPER_ERROR, "mapper error");
        }
    }

    @Override
    public BaseResult<List<UserBaseInfoDO>> selectSelective(UserBaseInfoDTO record) {
        if (null == record) {
            log.warn("UserBaseInfoService selectSelective param user is null");
            return BaseResult.errorReturn(null, ServiceCodeConstant.PARAM_ERROR, "param user is null");

        }

        try {
            return BaseResult.rightReturn(userBaseInfoMapper.selectSelective(UserBaseInfoDTO.convert2DO(record)));
        } catch (Exception e) {
            log.error("UserBaseInfoService selectSelective error {}", e);
            return BaseResult.errorReturn(null, ServiceCodeConstant.MAPPER_ERROR, "mapper error");
        }
    }
}
