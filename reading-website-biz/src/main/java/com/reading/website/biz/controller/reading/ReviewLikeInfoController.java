package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookReviewInfoQuery;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.domain.ReviewLikeInfoDO;
import com.reading.website.api.service.BookReviewInfoService;
import com.reading.website.api.service.ReviewLikeInfoService;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 点赞接口
 *
 * @xyang010 2019/6/9
 */
@Api(value = "点赞相关接口", description = "ReviewLikeInfoController", tags = {"点赞相关接口"})
@RestController
@Slf4j
@RequestMapping("/like")
public class ReviewLikeInfoController {
    private final ReviewLikeInfoService reviewLikeInfoService;

    @Autowired
    public ReviewLikeInfoController(ReviewLikeInfoService reviewLikeInfoService) {
        this.reviewLikeInfoService = reviewLikeInfoService;
    }

    @ApiOperation(value="点赞或取消点赞", notes="点赞或取消点赞")
    @GetMapping(value = "/save")
    public BaseResult<Integer> save(@RequestParam("reviewId") Integer reviewId, HttpServletRequest request) {

        // 获取登陆用户信息
        LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
        if (loginInfoDTO == null) {
            log.warn("ReviewLikeInfoController save user not login");
            return BaseResult.errorReturn(StatusCodeEnum.TOKEN_EXPIRE.getCode(), "TOKEN_EXPIRE");
        }

        // 点赞
        ReviewLikeInfoDO reviewLikeInfoDO = new ReviewLikeInfoDO();
        reviewLikeInfoDO.setReviewId(reviewId);
        reviewLikeInfoDO.setUserId(loginInfoDTO.getUserId());
        return reviewLikeInfoService.save(reviewLikeInfoDO);
    }
}
