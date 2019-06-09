package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.ReviewLikeInfoDO;

/**
 * 评论点赞服务接口
 *
 * @xyang010 2019/6/9
 */
public interface ReviewLikeInfoService {

    /**
     * 点赞或取消点赞
     * @param reviewLikeInfoDO
     * @return
     */
    BaseResult<Integer> save(ReviewLikeInfoDO reviewLikeInfoDO);

    /**
     * 查询点赞信息
     * @param userId
     * @param reviewId
     * @return
     */
    BaseResult<ReviewLikeInfoDO> selectByUserIdAndReviewId(Integer userId, Integer reviewId);

}
