package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;
import com.reading.website.api.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value="图书推荐", notes="图书推荐")
    @GetMapping(value = "/recommend")
    public BaseResult<List<BookDO>> recommend(@RequestParam("recommendType") String recommendType,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        Page page = new Page(pageNum, pageSize);
        return bookService.listRecommendBooks(recommendType, page);
    }

    @ApiOperation(value="根据小类id查询图书列表", notes="根据小类id查询图书列表")
    @GetMapping(value = "/smallCategory")
    public BaseResult<List<BookDO>> smallCategory(@RequestParam("smallCateId") Integer smallCateId,
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
        return bookService.pageQuery(query);
    }

    @ApiOperation(value="图书模糊查询", notes="图书模糊查询")
    @GetMapping(value = "/fuzzyQueryBook")
    public BaseResult<List<BookDO>> fuzzyQueryBook(@RequestParam("bookName") String bookName,
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
        return bookService.pageQuery(query);
    }


}
