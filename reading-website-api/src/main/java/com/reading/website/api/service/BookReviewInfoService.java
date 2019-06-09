package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;

import java.util.List;

/**
 * 图书评论服务接口
 *
 * @xyang010 2019/3/17
 */
public interface BookReviewInfoService {

    /**
     * 新增或更新
     * @param reviewInfoDO
     * @return
     */
    BaseResult<Integer> insertOrUpdate(BookReviewInfoDO reviewInfoDO);

    /**
     * 分页查询
     * @param query
     * @return
     */
    BaseResult<List<BookReviewInfoDO>> pageQuery(BookReviewInfoQuery query);

    /**
     * 更新点赞数
     * @param reviewId
     * @return
     */
    BaseResult<Integer> updateLikeNum(Integer reviewId, Boolean isAdd);

}
