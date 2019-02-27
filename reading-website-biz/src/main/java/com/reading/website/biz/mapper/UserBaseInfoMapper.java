package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户基本信息Mapper
 *
 * @yx8102    2019/1/7
 */
@Component
public interface UserBaseInfoMapper {

    /**
     * 新增用户信息
     * @param record
     * @return
     */
    int insertSelective(UserBaseInfoDO record);

    /**
     * 根据email修改用户信息
     * @param record
     * @return
     */
    int updateByEmailSelective(UserBaseInfoDO record);

    /**
     * 条件查询
     * @param query
     * @return
     */
    List<UserBaseInfoDO> selectSelective(UserBaseInfoQuery query);

}