package com.reading.website.biz.mapper;

import com.reading.website.api.domain.ReviewLikeInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ReviewLikeInfoMapper {

    /**
     * 点赞或取消
     * @param reviewLikeInfoDO
     * @return
     */
    int insertOrUpdate(ReviewLikeInfoDO reviewLikeInfoDO);

    /**
     * 查询点赞信息
     * @param userId 用户id
     * @param reviewId 评论id
     * @return
     */
    ReviewLikeInfoDO selectByUserIdAndReviewId(@Param("userId") Integer userId, @Param("reviewId") Integer reviewId);
}