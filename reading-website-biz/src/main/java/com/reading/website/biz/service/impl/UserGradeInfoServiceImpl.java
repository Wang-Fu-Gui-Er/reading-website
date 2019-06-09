package com.reading.website.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.UserGradeInfoDO;
import com.reading.website.api.service.UserGradeInfoService;
import com.reading.website.biz.mapper.UserGradeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户评分服务实现类
 *
 * @xyang010 2019/6/8
 */
@Slf4j
@Service
public class UserGradeInfoServiceImpl implements UserGradeInfoService {

    private final UserGradeInfoMapper userGradeInfoMapper;

    @Autowired
    public UserGradeInfoServiceImpl(UserGradeInfoMapper userGradeInfoMapper) {
        this.userGradeInfoMapper = userGradeInfoMapper;
    }


    @Override
    public BaseResult<Integer> insertOrUpdate(UserGradeInfoDO userGradeInfoDO) {

        if (userGradeInfoDO == null) {
            log.warn("UserGradeInfoServiceImpl insertOrUpdate param userGradeInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userReadingInfoDO is null");
        }

        try {
            return BaseResult.rightReturn(userGradeInfoMapper.insertOrUpdate(userGradeInfoDO));

        } catch (Exception e) {
            log.error("UserGradeInfoServiceImpl insertOrUpdate error, userGradeInfoDO {}, error",
                    JSON.toJSONString(userGradeInfoDO), e);

            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    @Override
    public BaseResult<UserGradeInfoDO> selectByUserIdAndBookId(Integer userId, Integer bookId) {

        if (userId == null || bookId == null) {
            log.warn("UserGradeInfoServiceImpl selectByUserId param userId or bookId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId or bookId is null");
        }

        try {
            return BaseResult.rightReturn(userGradeInfoMapper.selectByUserIdAndBookId(userId, bookId));

        } catch (Exception e) {
            log.error("UserGradeInfoServiceImpl selectByUserId error, userId {}, error", userId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
