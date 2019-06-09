package com.reading.website.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.ReviewLikeInfoDO;
import com.reading.website.api.service.BookReviewInfoService;
import com.reading.website.api.service.ReviewLikeInfoService;
import com.reading.website.biz.mapper.BookReviewInfoMapper;
import com.reading.website.biz.mapper.ReviewLikeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class comment
 *
 * @xyang010 2019/6/9
 */
@Service
@Slf4j
public class ReviewLikeInfoServiceImpl implements ReviewLikeInfoService {

    private final ReviewLikeInfoMapper reviewLikeInfoMapper;
    private final BookReviewInfoService bookReviewInfoService;

    @Autowired
    public ReviewLikeInfoServiceImpl(ReviewLikeInfoMapper reviewLikeInfoMapper,  BookReviewInfoService bookReviewInfoService) {
        this.reviewLikeInfoMapper = reviewLikeInfoMapper;
        this.bookReviewInfoService = bookReviewInfoService;
    }


    @Override
    public BaseResult<Integer> save(ReviewLikeInfoDO reviewLikeInfoDO) {
        if (reviewLikeInfoDO == null) {
            log.warn("ReviewLikeInfoServiceImpl save param reviewLikeInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(),
                    "param reviewLikeInfoDO is null");
        }
        Integer userId = reviewLikeInfoDO.getUserId();
        Integer reviewId = reviewLikeInfoDO.getReviewId();

        try {
            Boolean isLike = Boolean.TRUE;
            ReviewLikeInfoDO dbReviewLike = reviewLikeInfoMapper.selectByUserIdAndReviewId(userId, reviewId);
            if (dbReviewLike != null) {
                Boolean dbIsDeleted = dbReviewLike.getIsDeleted();
                if (dbIsDeleted) {
                    reviewLikeInfoDO.setIsDeleted(Boolean.FALSE);
                } else {
                    reviewLikeInfoDO.setIsDeleted(Boolean.TRUE);
                    isLike = Boolean.FALSE;
                }
            }
            reviewLikeInfoMapper.insertOrUpdate(reviewLikeInfoDO);
            return bookReviewInfoService.updateLikeNum(reviewId, isLike);
        } catch (Exception e) {
            log.error("ReviewLikeInfoServiceImpl save error, reviewLikeInfoDO {} error", JSON.toJSONString(reviewLikeInfoDO), e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    @Override
    public BaseResult<ReviewLikeInfoDO> selectByUserIdAndReviewId(Integer userId, Integer reviewId) {
        if (userId == null || reviewId == null) {
            log.warn("ReviewLikeInfoServiceImpl selectByUserIdAndReviewId param userId or reviewId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(),
                    "param userId or reviewId is null");
        }

        try {
            return BaseResult.rightReturn(reviewLikeInfoMapper.selectByUserIdAndReviewId(userId, reviewId));
        } catch (Exception e) {
            log.error("ReviewLikeInfoServiceImpl selectByUserIdAndReviewId error, userId {} reviewId{} error", userId, reviewId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
