package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserBaseInfoDO;
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
     * 根据userId修改用户信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UserBaseInfoDO record);

    /**
     * 条件查询
     * @param record
     * id,ids,nickName,mobileNum,weChatId,weiboName,email
     * @return
     */
    List<UserBaseInfoDO> selectSelective(UserBaseInfoDO record);

}