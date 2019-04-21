package com.reading.website.api.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 登陆信息实体
 *
 * @xyang010 2019/4/21
 */
@Data
public class LoginInfoDTO implements Serializable {

    // 用户id
    private Integer userId;

    // 邮箱地址
    private String email;

    public LoginInfoDTO() {
    }

    public LoginInfoDTO(Integer userId, String email) {
        this.userId = userId;
        this.email = email;
    }
}
