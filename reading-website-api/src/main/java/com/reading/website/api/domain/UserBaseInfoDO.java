package com.reading.website.api.domain;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户基础信息类
 *
 * @yx8102 2019/1/7
 */
@Data
public class UserBaseInfoDO implements Serializable {

    /**
     * 主键userId
     */
    private Long id;

    /**
     * userId List
     */
    private List<Long> ids;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 是否被删除
     */
    private Boolean isDeleted;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 登录密码
     */
    private String password;

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

    /**
     * 头像地址
     */
    private String headPicPath;

    /**
     * 是否是后台管理员
     */
    private Boolean isAdmin;
}