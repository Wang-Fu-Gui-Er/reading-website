package com.reading.website.api.domain;

import com.reading.website.api.base.Page;
import lombok.Data;

/**
 * 反馈信息查询类
 *
 * @xyang010 2019/3/21
 */
@Data
public class AdviceInfoQuery extends Page {
    // 反馈信息id
    private Integer id;

    // 用户id
    private Integer userId;

    // 反馈类型
    private Integer type;

    // 反馈进度
    private Integer status;
}
