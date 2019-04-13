package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 用户基本信息查询类
 *
 * @xyang010 2019/1/10
 */
@ApiModel(value="用户基本信息查询对象")
@Data
public class UserBaseInfoQuery {
    /**
     * 主键userId
     */
    private Integer id;

    /**
     * userId列表
     */
    private List<Integer> ids;

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

    /**
     * 是否是后台管理员
     */
    private Boolean isAdmin;

    /**
     * 用户状态：0未激活，1正常
     */
    private Integer status;
}
