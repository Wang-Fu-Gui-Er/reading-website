package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 阅读记录信息Mapper
 *
 * @yx8102 2019/3/13
 */
@Component
public interface UserReadingInfoMapper {

    /**
     * 增加或更新
     * @param userReadingInfoDO 阅读记录实体
     * @return
     */
    int insertOrUpdate(UserReadingInfoDO userReadingInfoDO);


    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    List<UserReadingInfoDO> pageQuery(UserReadingInfoQuery query);

    /**
     * 计数
     * @param query 查询条件
     * @return
     */
    int count(UserReadingInfoQuery query);


}