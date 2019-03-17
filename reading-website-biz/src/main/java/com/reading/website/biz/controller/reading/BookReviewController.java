package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;
import com.reading.website.api.service.BookReviewInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 书评接口
 *
 * @xyang010 2019/3/17
 */
@Api(value = "图书评论接口", description = "BookReviewController", tags = {"图书列表接口"})
@RestController
@Slf4j
@RequestMapping("/review")
public class BookReviewController {

    @Autowired
    private BookReviewInfoService reviewInfoService;

    @ApiOperation(value="新增或修改图书评论", notes="新增或修改图书评论")
    @PostMapping(value = "/addOrUpdate")
    public BaseResult<Integer> addOrUpdate(@RequestBody BookReviewInfoDO reviewInfoDO) {
        return reviewInfoService.insertOrUpdate(reviewInfoDO);
    }

    @ApiOperation(value="查询图书评论", notes="查询图书评论")
    @PostMapping(value = "/query")
    public BaseResult<List<BookReviewInfoDO>> query(@RequestBody BookReviewInfoQuery query) {
        return reviewInfoService.pageQuery(query);
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
