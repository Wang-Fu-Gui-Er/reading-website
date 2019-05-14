package com.reading.website.biz.controller.reading;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.SearchTypeConstant;
import com.reading.website.api.domain.*;
import com.reading.website.api.service.AuthorService;
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

    private final BookService bookService;

    private final UserReadingService readingService;

    private final BookLogic bookLogic;

    private final AuthorService authorService;

    @Autowired
    public BookListController(BookService bookService, UserReadingService readingService, BookLogic bookLogic, AuthorService authorService) {
        this.bookService = bookService;
        this.readingService = readingService;
        this.bookLogic = bookLogic;
        this.authorService = authorService;
    }

    @ApiOperation(value="图书推荐", notes="图书推荐")
    @GetMapping(value = "/recommend")
    public BaseResult<List<BookInfoVO>> recommend(@RequestParam("recommendType") String recommendType,
                                                  @RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page(pageNum, pageSize);
        BaseResult<List<BookInfoVO>> bookRes = bookService.listRecommendBooks(recommendType, page);
        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
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
        BaseResult<List<BookInfoVO>> bookRes = bookService.pageQuery(query);

        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
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
        BaseResult<List<BookInfoVO>> bookRes = bookService.pageQuery(query);

        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
        return BaseResult.rightReturn(bookInfoVOList, bookRes.getPage());
    }

    @ApiOperation(value="查询加入书架的图书", notes="查询加入书架的图书")
    @GetMapping(value = "/onShelf")
    public BaseResult<List<BookInfoVO>> onShelf(@RequestParam("userId") Integer userId,
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
        BaseResult<List<BookInfoVO>> bookRes = bookService.pageQuery(bookInfoQuery);
        if (!bookRes.getSuccess()) {
            log.warn("queryReadingHistory, query bookInfo error, bookIds is {}, bookRes {}", bookIds, bookRes);
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "query readingInfo error");
        }

        //3. 拼装返回结果
        BaseResult<List<BookInfoVO>> result = new BaseResult<>();
        result.setSuccess(true);
        result.setData(bookRes.getData());
        result.setPage(new Page(readingInfoQuery.getPageNum(), readingInfoQuery.getPageSize(), readingRes.getPage().getTotalNum()));
        return result;
    }

    @ApiOperation(value="查询全部图书", notes="查询全部图书")
    @GetMapping(value = "/all")
    public BaseResult<List<BookInfoVO>> getAllBooks(@RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        BookInfoQuery query = new BookInfoQuery();
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        BaseResult<List<BookInfoVO>> bookRes = bookService.pageQuery(query);

        if (!bookRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
        }

        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
        return BaseResult.rightReturn(bookInfoVOList, bookRes.getPage());
    }

    @ApiOperation(value="搜索框模糊查询", notes="搜索框模糊查询")
    @GetMapping(value = "/fuzzyQueryAll")
    public BaseResult<List<BookInfoVO>> fuzzyQueryAll(@RequestParam("searchType") String searchType,
                                                      @RequestParam("searchKey") String searchKey,
                                                      @RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize) {
        boolean paramError = false;
        if (StringUtils.isEmpty(searchType)) {
            paramError = true;
            log.warn("BookListController fuzzyQueryAll, searchType is empty");
        }

        if (StringUtils.isEmpty(searchKey)) {
            paramError = true;
            log.warn("BookListController fuzzyQueryAll, searchKey is empty");
        }

        if (paramError) {
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "参数异常");
        }

        // 根据图书模糊查询
        if (searchType.equals(SearchTypeConstant.BOOK)) {
            return fuzzyQueryBook(searchKey, pageNum, pageSize);
        }

        // 根据作者模糊查询
        if (searchType.equals(SearchTypeConstant.AUTHOR)) {
            BaseResult<List<AuthorDO>> authorRes = authorService.fuzzySelectByAuthorName(searchKey);
            if (!authorRes.getSuccess()) {
                log.warn("模糊查询作者信息异常, searchKey {}, authorRes {}", searchKey, authorRes);
                return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "查询作者信息失败");
            }

            List<AuthorDO> authorDOList = authorRes.getData();
            if (CollectionUtils.isEmpty(authorDOList)) {
                log.warn("模糊查询作者信息为空, searchKey {}", searchKey);
                return BaseResult.errorReturn(StatusCodeEnum.NOT_FOUND.getCode(), "未收录该作者信息，可提反馈意见，工作人员将补录");
            }

            List<Integer> authorIds = authorDOList.stream().map(AuthorDO::getId).distinct().collect(Collectors.toList());
            BookInfoQuery bookInfoQuery = new BookInfoQuery();
            bookInfoQuery.setAuthorIds(authorIds);
            bookInfoQuery.setPageNum(pageNum);
            bookInfoQuery.setPageSize(pageSize);
            BaseResult<List<BookInfoVO>> bookRes = bookService.pageQuery(bookInfoQuery);

            if (!bookRes.getSuccess()) {
                return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "内部服务异常");
            }

            List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(bookRes.getData());
            return BaseResult.rightReturn(bookInfoVOList, bookRes.getPage());
        }

        return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "查询类型为非法值");
    }

    @ApiOperation(value="相似推荐", notes="相似推荐")
    @GetMapping(value = "/similarRecommend")
    public BaseResult<List<BookInfoVO>> similarRecommend(@RequestParam("smallCateId") Integer smallCateId) {
        if (smallCateId == null) {
            log.warn("相似推荐 smallCateId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "smallCateId is null");
        }
        return bookService.listSimilarRecommendBooks(smallCateId);
    }


}
