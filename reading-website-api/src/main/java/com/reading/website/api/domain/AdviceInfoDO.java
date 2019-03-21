package com.reading.website.api.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 作者信息
 *
 * @yx8102 2019/3/21
 */
@ApiModel(value = "反馈信息")
@Data
public class AdviceInfoDO {

    // 反馈信息id
    private Integer id;

    private Date created;

    private Date updated;

    private Boolean isDeleted;

    // 用户id
    private Integer userId;

    // 反馈类型
    private Integer type;

    // 反馈标题
    private String title;

    // 反馈详细描述
    private String detail;

    // 邮箱地址
    private String email;

    // 反馈进度
    private Integer status;

}