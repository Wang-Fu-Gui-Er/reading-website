package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.AdviceInfoDO;
import com.reading.website.api.domain.AdviceInfoQuery;

import java.util.List;

/**
 * 反馈服务
 *
 * @xyang010 2019/3/21
 */
public interface AdviceInfoService {

    /**
     * 新增
     * @param adviceInfoDO 反馈信息
     * @return
     */
    BaseResult<Integer> insert(AdviceInfoDO adviceInfoDO);

    /**
     * 修改
     * @param adviceInfoDO 反馈信息
     * @return
     */
    BaseResult<Integer> update(AdviceInfoDO adviceInfoDO);

    /**
     * 条件查询
     * @param query 查询条件
     * @return
     */
    BaseResult<List<AdviceInfoDO>> pageQuery(AdviceInfoQuery query);
}
