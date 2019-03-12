package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 图书相关接口
 *
 * @xyang010 2019/3/11
 */
@Api(value = "图书相关接口", description = "BookController", tags = {"图书相关接口"})
@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value="查询图书信息", notes="查询图书信息")
    @GetMapping(value = "/getBookInfo")
    public BaseResult<BookDO> getBookInfo(@RequestParam("bookId") Integer bookId) {
        return bookService.selectByBookId(bookId);
    }

    @ApiOperation(value="新增或修改图书信息", notes="新增或修改图书信息")
    @PostMapping(value = "/addOrUpdateBook")
    public BaseResult<Integer> addOrUpdateBook(@RequestBody BookDO bookDO) {
        return bookService.insertOrUpdateBook(bookDO);
    }

    @ApiOperation(value="删除图书信息", notes="删除图书信息")
    @GetMapping(value = "/del")
    public BaseResult<Boolean> delBook(@RequestParam("bookId") Integer bookId) {
        return bookService.delByBookId(bookId);
    }

}
