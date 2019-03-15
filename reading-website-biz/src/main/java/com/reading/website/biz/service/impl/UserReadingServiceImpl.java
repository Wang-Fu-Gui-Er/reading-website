package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;
import com.reading.website.api.service.UserReadingService;
import com.reading.website.biz.mapper.UserReadingInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户阅读服务实现
 *
 * @xyang010 2019/3/13
 */
@Service
@Slf4j
public class UserReadingServiceImpl implements UserReadingService {
    @Autowired
    private UserReadingInfoMapper userReadingInfoMapper;

    /**
     * 增加或更新
     * @param userReadingInfoDO 阅读记录实体
     * @return
     */
    @Override
    public BaseResult<Integer> insertOrUpdate(UserReadingInfoDO userReadingInfoDO) {
        if (userReadingInfoDO == null) {
            log.warn("UserReadingServiceImpl insertOrUpdate param userReadingInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userReadingInfoDO is null");
        }

        try {
            return BaseResult.rightReturn(userReadingInfoMapper.insertOrUpdate(userReadingInfoDO));
        } catch (Exception e) {
            log.error("UserReadingServiceImpl insertOrUpdate failed, userReadingInfoDO {}, error {},", userReadingInfoDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    @Override
    public BaseResult<List<UserReadingInfoDO>> pageQuery(UserReadingInfoQuery query) {
        if (query == null) {
            log.warn("UserReadingServiceImpl pageQuery param query is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param query is null");
        }

        try {

            BaseResult<List<UserReadingInfoDO>> result = new BaseResult<>();
            result.setSuccess(true);
            result.setData(userReadingInfoMapper.pageQuery(query));
            Page page = new Page(query.getPageNum(), query.getPageSize());
            page.setTotalNum(userReadingInfoMapper.count(query));
            result.setPage(page);
            return result;
        } catch (Exception e) {
            log.error("UserReadingServiceImpl pageQuery failed, query {}, error {},", query, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
