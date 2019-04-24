package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookSaveDTO;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.service.BookService;
import com.reading.website.api.service.UserReadingService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.biz.logic.BookLogic;
import com.reading.website.biz.logic.ChapterLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @Autowired
    private UserReadingService readingService;

    @Autowired
    private BookLogic bookLogic;

    @Autowired
    private ChapterLogic chapterLogic;

    @ApiOperation(value="查询图书信息", notes="查询图书信息")
    @GetMapping(value = "/getBookInfo")
    public BaseResult<BookInfoVO> getBookInfo(@RequestParam("bookId") Integer bookId) {
        BaseResult<BookInfoVO> queryBookRes = bookService.selectByBookId(bookId);
        if (!queryBookRes.getSuccess()) {
            return queryBookRes;
        }

        return BaseResult.rightReturn(bookLogic.assemblyCategory(queryBookRes.getData()));
    }

    @ApiOperation(value="新增或修改图书信息", notes="新增或修改图书信息")
    @PostMapping(value = "/addOrUpdateBook")
    public BaseResult<Integer> addOrUpdateBook(@RequestBody BookSaveDTO bookSaveDTO) {
        if (bookSaveDTO == null) {
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param is null");
        }
        //1. 保存图书信息
        BaseResult<Integer> bookRes = bookService.insertOrUpdateBook(bookSaveDTO.getBookDO());
        if (!bookRes.getSuccess()) {
            return bookRes;
        }

        //2. 保存相关章节信息
        List<ChapterDO> chapterDOList = bookSaveDTO.getChapterDOList();
        if (!CollectionUtils.isEmpty(chapterDOList)) {
            int bookId = bookRes.getData();
            chapterDOList.forEach(chapterDO -> {
                chapterDO.setBookId(bookId);
            });

            chapterLogic.batchInsertChapter(chapterDOList);
        }

        return bookRes;
    }

    @ApiOperation(value="删除图书信息", notes="删除图书信息")
    @GetMapping(value = "/del")
    public BaseResult<Boolean> delBook(@RequestParam("bookId") Integer bookId) {
        return bookService.delByBookId(bookId);
    }

    @ApiOperation(value="添加到书架或移出书架", notes="添加到书架或移出书架")
    @GetMapping(value = "/addOrRemoveToShelf")
    public BaseResult<Integer> addOrRemoveToShelf(@RequestParam("userId") Integer userId,
                                       @RequestParam("bookId") Integer bookId,
                                       @RequestParam("onShelf") Boolean onShelf) {

        if (userId == null || bookId == null || onShelf == null) {
            log.warn("onShelf param userId or bookId or onShelf is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId or bookId or onShelf is null");
        }

        UserReadingInfoDO userReadingInfoDO = new UserReadingInfoDO();
        userReadingInfoDO.setUserId(userId);
        userReadingInfoDO.setBookId(bookId);
        userReadingInfoDO.setIsOnShelf(onShelf);
        return readingService.insertOrUpdate(userReadingInfoDO);
    }
}
