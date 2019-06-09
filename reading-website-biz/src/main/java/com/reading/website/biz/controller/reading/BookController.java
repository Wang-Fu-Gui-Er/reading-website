package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookSaveDTO;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.service.BookService;
import com.reading.website.api.service.ChapterService;
import com.reading.website.api.service.UserReadingService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.biz.enums.ChapterSortEnum;
import com.reading.website.biz.logic.BookLogic;
import com.reading.website.biz.logic.ChapterLogic;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
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

    private final BookService bookService;

    private final UserReadingService readingService;

    private final BookLogic bookLogic;

    private final ChapterLogic chapterLogic;

    private final ChapterService chapterService;

    @Autowired
    public BookController(BookService bookService, UserReadingService readingService, BookLogic bookLogic, ChapterLogic chapterLogic, ChapterService chapterService) {
        this.bookService = bookService;
        this.readingService = readingService;
        this.bookLogic = bookLogic;
        this.chapterLogic = chapterLogic;
        this.chapterService = chapterService;
    }

    @ApiOperation(value="查询图书信息", notes="查询图书信息")
    @GetMapping(value = "/getBookInfo")
    public BaseResult<BookInfoVO> getBookInfo(@RequestParam("bookId") Integer bookId) {
        BaseResult<BookInfoVO> queryBookRes = bookService.selectByBookId(bookId);
        if (!queryBookRes.getSuccess()) {
            return queryBookRes;
        }
        BookInfoVO bookInfoVO = queryBookRes.getData();
        List<BookInfoVO> bookInfoVOList = bookLogic.assemblyGrade(Collections.singletonList(bookInfoVO));
        return BaseResult.rightReturn(bookLogic.assemblyCategory(bookInfoVOList.get(0)));
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
        List<ChapterDO> chapterDOList = bookSaveDTO.getChapterList();
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
        // 删除图书
        BaseResult<Boolean> delBookRes = bookService.delByBookId(bookId);

        // 删除图书对应章节
        chapterService.debByBookId(bookId);

        return delBookRes;
    }

    @ApiOperation(value="添加到书架或移出书架", notes="添加到书架或移出书架")
    @GetMapping(value = "/addOrRemoveToShelf")
    public BaseResult<Integer> addOrRemoveToShelf(@RequestParam("userId") Integer userId,
                                                  @RequestParam("bookId") Integer bookId,
                                                  @RequestParam("onShelf") Boolean onShelf,
                                                  HttpServletRequest request) {

        if (bookId == null || onShelf == null) {
            log.warn("onShelf param userId or bookId or onShelf is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId or bookId or onShelf is null");
        }

        if (userId == null) {
            LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
            if (loginInfoDTO != null) {
                userId = loginInfoDTO.getUserId();
            }
        }

        UserReadingInfoDO userReadingInfoDO = new UserReadingInfoDO();
        userReadingInfoDO.setUserId(userId);
        userReadingInfoDO.setBookId(bookId);
        userReadingInfoDO.setIsOnShelf(onShelf);

        // 加入书架，记录图书第一章的章节id
        if (onShelf) {
            BaseResult<List<ChapterDO>> chapRes = chapterService.selectByBookId(bookId, ChapterSortEnum.ASC.getType());
            if (!chapRes.getSuccess()) {
                log.warn("查询图书章节失败 bookId {}, result {}", bookId, chapRes);
            }

            List<ChapterDO> chapterDOList = chapRes.getData();
            if (!CollectionUtils.isEmpty(chapterDOList)) {
                userReadingInfoDO.setChapId(chapterDOList.get(0).getId());
            }
        }
        return readingService.insertOrUpdate(userReadingInfoDO);
    }
}
