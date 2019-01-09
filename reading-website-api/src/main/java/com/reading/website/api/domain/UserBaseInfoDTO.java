package com.reading.website.api.domain;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户基本信息DTO
 *
 * @yx8102 2019/1/7
 */
@Data
public class UserBaseInfoDTO implements Serializable {

    /**
     * 主键userId
     */
    private Long id;

    /**
     * userId列表
     */
    private List<Long> ids;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobileNum;

    /**
     * 微信ID
     */
    private String weChatId;

    /**
     * 微博昵称
     */
    private String weiboName;

    /**
     * 邮箱地址
     */
    private String email;

    public static UserBaseInfoDO convert2DO(UserBaseInfoDTO userBaseInfoDTO) {
        if (userBaseInfoDTO == null) {
            return null;
        }

        UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
        BeanUtils.copyProperties(userBaseInfoDTO, userBaseInfoDO);
        return userBaseInfoDO;
    }

    public static List<UserBaseInfoDO> convert2DOs(List<UserBaseInfoDTO> userBaseInfoDTOs) {
        List<UserBaseInfoDO> doList = new ArrayList<>();
        if (CollectionUtils.isEmpty(userBaseInfoDTOs)) {
            return doList;
        }

        userBaseInfoDTOs.forEach(userBaseInfoDO -> {
            doList.add(convert2DO(userBaseInfoDO));
        });

        return doList;
    }

}
