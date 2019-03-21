package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.AdviceInfoDO;
import com.reading.website.api.domain.AdviceInfoQuery;
import com.reading.website.api.enums.AdviceHandleStatusEnum;
import com.reading.website.api.service.AdviceInfoService;
import com.reading.website.biz.mapper.AdviceInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 反馈服务实现
 *
 * @xyang010 2019/3/21
 */
@Service
@Slf4j
public class AdviceInfoServiceImpl implements AdviceInfoService {

    @Autowired
    private AdviceInfoMapper adviceInfoMapper;

    /**
     * 新增
     * @param adviceInfoDO 反馈信息
     * @return
     */
    @Override
    public BaseResult<Integer> insert(AdviceInfoDO adviceInfoDO) {
        if (adviceInfoDO == null) {
            log.warn("AdviceInfoServiceImpl insert param error, param adviceInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param adviceInfoDO is null");
        }

        try {
            if (!AdviceHandleStatusEnum.PENDING.getCode().equals(adviceInfoDO.getStatus())) {
                adviceInfoDO.setStatus(AdviceHandleStatusEnum.PENDING.getCode());
            }

            return BaseResult.rightReturn(adviceInfoMapper.insertSelective(adviceInfoDO));
        } catch (Exception e) {
            log.warn("AdviceInfoServiceImpl insert  error, param adviceInfoDO {}, error {}", adviceInfoDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");

        }
    }

    /**
     * 修改
     * @param adviceInfoDO 反馈信息
     * @return
     */
    @Override
    public BaseResult<Integer> update(AdviceInfoDO adviceInfoDO) {
        if (adviceInfoDO == null) {
            log.warn("AdviceInfoServiceImpl update param error, param adviceInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param adviceInfoDO is null");
        }

        try {
            return BaseResult.rightReturn(adviceInfoMapper.updateByPrimaryKeySelective(adviceInfoDO));
        } catch (Exception e) {
            log.warn("AdviceInfoServiceImpl update  error, param adviceInfoDO {}, error {}", adviceInfoDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");

        }
    }

    /**
     * 条件查询
     * @param query 查询条件
     * @return
     */
    @Override
    public BaseResult<List<AdviceInfoDO>> pageQuery(AdviceInfoQuery query) {
        if (query == null) {
            log.warn("AdviceInfoServiceImpl pageQuery param error, param query is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param query is null");
        }

        try {
            List<AdviceInfoDO> adviceInfoDOList = adviceInfoMapper.pageQuery(query);
            Page page = new Page(query.getPageNum(), query.getPageSize(), adviceInfoMapper.count(query));
            return BaseResult.rightReturn(adviceInfoDOList, page);
        } catch (Exception e) {
            log.warn("AdviceInfoServiceImpl pageQuery error, param query {}, error {}", query, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");

        }
    }
}
