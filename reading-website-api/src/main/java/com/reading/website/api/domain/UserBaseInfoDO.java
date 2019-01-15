package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="用户对象")
public class UserBaseInfoDO implements Serializable {

    /**
     * 主键userId
     */
    @ApiModelProperty(value="用户id")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getWeiboName() {
        return weiboName;
    }

    public void setWeiboName(String weiboName) {
        this.weiboName = weiboName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        this.headPicPath = headPicPath;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }
}