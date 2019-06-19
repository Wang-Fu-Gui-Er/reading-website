package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.service.BookReviewInfoService;
import com.reading.website.api.vo.BookReviewVO;
import com.reading.website.biz.enums.MatchType;
import com.reading.website.biz.logic.ReviewLogic;
import com.reading.website.biz.utils.SensitiveWordFilterUtil;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 书评接口
 *
 * @xyang010 2019/3/17
 */
@Api(value = "图书评论接口", description = "BookReviewController", tags = {"图书评论接口"})
@RestController
@Slf4j
@RequestMapping("/review")
public class BookReviewController {

    private final BookReviewInfoService reviewInfoService;

    private final ReviewLogic reviewLogic;

    private final SensitiveWordFilterUtil sensitiveWordFilterUtil;

    @Autowired
    public BookReviewController(BookReviewInfoService reviewInfoService, ReviewLogic reviewLogic, SensitiveWordFilterUtil sensitiveWordFilterUtil) {
        this.reviewInfoService = reviewInfoService;
        this.reviewLogic = reviewLogic;
        this.sensitiveWordFilterUtil = sensitiveWordFilterUtil;
    }


    @ApiOperation(value="新增或修改图书评论", notes="新增或修改图书评论")
    @PostMapping(value = "/addOrUpdate")
    public BaseResult<Integer> addOrUpdate(@RequestBody BookReviewInfoDO reviewInfoDO, HttpServletRequest request) {
        //1. 判断用户是否登陆
        if (reviewInfoDO.getUserId() == null) {
            LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
            if (loginInfoDTO != null) {
                reviewInfoDO.setUserId(loginInfoDTO.getUserId());
            } else {
                return BaseResult.errorReturn(StatusCodeEnum.TOKEN_EXPIRE.getCode(), "TOKEN_EXPIRE");
            }
        }


        //2. 敏感词过滤
        String comment = reviewInfoDO.getComment();
        int length = sensitiveWordFilterUtil.checkSensitiveWord(comment, 0, MatchType.MIN_MATCH);
        if (length > 0) {
            return BaseResult.errorReturn(StatusCodeEnum.REVIEW_VIOLATIONS.getCode(),
                    "评论包含敏感词，请重新填写");
        }
        return reviewInfoService.insertOrUpdate(reviewInfoDO);
    }

    @ApiOperation(value="查询图书评论", notes="查询图书评论")
    @PostMapping(value = "/query")
    public BaseResult<List<BookReviewVO>> query(@RequestBody BookReviewInfoQuery query, HttpServletRequest request) {
        Integer loginUserId = null;
        LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
        if (loginInfoDTO != null) {
            loginUserId = loginInfoDTO.getUserId();
        }
        return reviewLogic.queryReview(query, loginUserId);
    }

    @ApiOperation(value="删除图书评论", notes="删除图书评论")
    @GetMapping(value = "/del")
    public BaseResult<Integer> del(@RequestParam("reviewId") Integer reviewId) {
        BookReviewInfoDO reviewInfoDO = new BookReviewInfoDO();
        reviewInfoDO.setId(reviewId);
        reviewInfoDO.setIsDeleted(true);
        return reviewInfoService.insertOrUpdate(reviewInfoDO);
    }

}
