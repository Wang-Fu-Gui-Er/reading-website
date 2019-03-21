package com.reading.website.biz.mapper;

import com.reading.website.api.domain.AdviceInfoDO;
import com.reading.website.api.domain.AdviceInfoQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 反馈Mapper
 *
 * @yx8102 2019/3/21
 */
@Component
public interface AdviceInfoMapper {

    /**
     * 新增
     * @param adviceInfoDO 反馈信息
     * @return
     */
    int insertSelective(AdviceInfoDO adviceInfoDO);

    /**
     * 根据反馈信息id修改
     * @param adviceInfoDO 反馈信息
     * @return
     */
    int updateByPrimaryKeySelective(AdviceInfoDO adviceInfoDO);


    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    List<AdviceInfoDO> pageQuery(AdviceInfoQuery query);

    /**
     * 计数
     * @param query 查询条件
     * @return
     */
    int count(AdviceInfoQuery query);
}