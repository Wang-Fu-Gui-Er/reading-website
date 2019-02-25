package com.reading.website.api.base;

import lombok.Data;

/**
 * 分页工具类
 *
 * @xyang010 2019/1/21
 */
@Data
public class Page {

    /**
     * 当前页
     */
    private Integer pageNum = 1;

    /**
     * 每页显示的总条数
     */
    private Integer pageSize = 10;

    /**
     * 总条数
     */
    private Integer totalNum;

    /**
     * mybatis分页查询
     */
    private Integer offset = (pageNum > 0 ? pageNum - 1 : 0) * pageSize;
}
