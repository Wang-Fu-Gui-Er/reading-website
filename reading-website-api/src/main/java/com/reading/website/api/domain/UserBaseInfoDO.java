package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户基础信息类
 *
 * @yx8102 2019/1/7
 */
@ApiModel(value="用户对象")
@Data
public class UserBaseInfoDO implements Serializable {

    /**
     * 主键userId
     */
    private Integer id;

    /**
     * userId List
     */
    private List<Integer> ids;

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

    /**
     * 用户状态：0未激活，1正常
     */
    private Integer status;

}