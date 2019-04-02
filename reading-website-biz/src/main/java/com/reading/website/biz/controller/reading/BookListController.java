package com.reading.website.biz.controller.reading;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;
import com.reading.website.api.service.BookService;
import com.reading.website.api.service.UserReadingService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.biz.logic.BookLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 图书列表接口
 *
 * @xyang010 2019/3/12
 */
@Api(value = "图书列表接口", description = "BookListController", tags = {"图书列表接口"})
@RestController
@Slf4j
@RequestMapping("/book/list")
public class BookListController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserReadingService readingService;

    @Autowired
    private BookLogic bookLogic;

    @ApiOperation(value="图书推荐", notes="图书推荐")
    @GetMapping(value = "/recommend")
    public BaseResult<List<BookInfoVO>> recommend(@RequestParam("recommendType") String recommendType,
                                                  @RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page(pageNum, pageSize);
        BaseResult<List<BookDO>> bookRes = bookService.listRecommendBooks(recommendType, page);
        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
        if (CollectionUtils.isEmpty(bookInfoVOList)) {
            return BaseResult.errorReturn(StatusCodeEnum.LOGIC_ERROR.getCode(), "获取图书评分信息异常");
        }
        return BaseResult.rightReturn(bookInfoVOList, bookRes.getPage());

    }

    @ApiOperation(value="根据小类id查询图书列表", notes="根据小类id查询图书列表")
    @GetMapping(value = "/smallCategory")
    public BaseResult<List<BookInfoVO>> smallCategory(@RequestParam("smallCateId") Integer smallCateId,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        if (smallCateId == null) {
            log.warn("根据小类id查询图书列表失败，参数异常，smallCateId is null.");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "smallCateI为null");
        }

        BookInfoQuery query = new BookInfoQuery();
        query.setSmallCateId(smallCateId);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        BaseResult<List<BookDO>> bookRes = bookService.pageQuery(query);

        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
        if (CollectionUtils.isEmpty(bookInfoVOList)) {
            return BaseResult.errorReturn(StatusCodeEnum.LOGIC_ERROR.getCode(), "获取图书评分信息异常");
        }
        return BaseResult.rightReturn(bookInfoVOList, bookRes.getPage());

    }

    @ApiOperation(value="图书模糊查询", notes="图书模糊查询")
    @GetMapping(value = "/fuzzyQueryBook")
    public BaseResult<List<BookInfoVO>> fuzzyQueryBook(@RequestParam("bookName") String bookName,
                                                  @RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize) {
        if (StringUtils.isEmpty(bookName)) {
            log.warn("图书模糊查询失败，参数异常，bookName is null.");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "bookName为null");
        }

        BookInfoQuery query = new BookInfoQuery();
        query.setBookName(bookName);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        BaseResult<List<BookDO>> bookRes = bookService.pageQuery(query);

        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
        if (CollectionUtils.isEmpty(bookInfoVOList)) {
            return BaseResult.errorReturn(StatusCodeEnum.LOGIC_ERROR.getCode(), "获取图书评分信息异常");
        }
        return BaseResult.rightReturn(bookInfoVOList, bookRes.getPage());
    }

    @ApiOperation(value="查询加入书架的图书", notes="查询加入书架的图书")
    @GetMapping(value = "/onShelf")
    public BaseResult<List<BookDO>> onShelf(@RequestParam("userId") Integer userId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize) {
        if (userId == null) {
            log.warn("queryReadingHistory param userId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId is null");
        }

        //1. 查询阅读记录
        UserReadingInfoQuery readingInfoQuery = new UserReadingInfoQuery();
        readingInfoQuery.setUserId(userId);
        readingInfoQuery.setPageNum(pageNum);
        readingInfoQuery.setPageSize(pageSize);
        readingInfoQuery.setIsOnShelf(true);
        BaseResult<List<UserReadingInfoDO>> readingRes = readingService.pageQuery(readingInfoQuery);
        if (!readingRes.getSuccess()) {
            log.warn("query onShelf book, query readingInfo error, userId is {}, readingRes {}", userId, readingRes);
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "query readingInfo error");
        }

        List<UserReadingInfoDO> readingInfoList = readingRes.getData();
        if (CollectionUtils.isEmpty(readingInfoList)) {
            log.warn("query onShelf book, readingInfo is empty");
            return BaseResult.rightReturn(null);
        }

        //2. 查询图书信息
        List<Integer> bookIds = readingInfoList
                .stream()
                .map(UserReadingInfoDO::getBookId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        BookInfoQuery bookInfoQuery = new BookInfoQuery();
        bookInfoQuery.setBookIds(bookIds);
        bookInfoQuery.setPageSize(bookIds.size());
        BaseResult<List<BookDO>> bookRes = bookService.pageQuery(bookInfoQuery);
        if (!bookRes.getSuccess()) {
            log.warn("queryReadingHistory, query bookInfo error, bookIds is {}, bookRes {}", bookIds, bookRes);
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "query readingInfo error");
        }

        //3. 拼装返回结果
        BaseResult<List<BookDO>> result = new BaseResult<>();
        result.setSuccess(true);
        result.setData(bookRes.getData());
        result.setPage(new Page(readingInfoQuery.getPageNum(), readingInfoQuery.getPageSize(), readingRes.getPage().getTotalNum()));
        return result;
    }


}
