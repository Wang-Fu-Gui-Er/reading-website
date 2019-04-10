package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.*;
import com.reading.website.api.service.BookGradeInfoService;
import com.reading.website.api.service.BookService;
import com.reading.website.api.service.ChapterService;
import com.reading.website.api.service.UserReadingService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.api.vo.ReadingHistoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户阅读记录接口
 *
 * @xyang010 2019/3/13
 */
@Api(value = "用户阅读相关接口", description = "UserReadingController", tags = {"用户阅读相关接口"})
@RestController
@Slf4j
@RequestMapping("/reading")
public class UserReadingController {

    @Autowired
    private UserReadingService readingService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private BookGradeInfoService gradeInfoService;

    @ApiOperation(value="查询阅读历史", notes="查询阅读历史")
    @GetMapping(value = "/history")
    public BaseResult<List<ReadingHistoryVO>> queryReadingHistory(@RequestParam("userId") Integer userId,
                                                                  @RequestParam("pageNum") Integer pageNum,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        if (userId == null) {
            log.warn("queryReadingHistory param userId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId is null");
        }

        //1. 查询阅读记录
        UserReadingInfoQuery query = new UserReadingInfoQuery();
        query.setUserId(userId);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        BaseResult<List<UserReadingInfoDO>> readingRes = readingService.pageQuery(query);
        if (!readingRes.getSuccess()) {
            log.warn("queryReadingHistory, query readingInfo error, userId is {}, readingRes {}", userId, readingRes);
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "query readingInfo error");
        }

        List<UserReadingInfoDO> readingInfoList = readingRes.getData();
        if (CollectionUtils.isEmpty(readingInfoList)) {
            log.warn("queryReadingHistory, readingInfo is empty");
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

        List<BookInfoVO> bookList = bookRes.getData();
        if (CollectionUtils.isEmpty(bookList)) {
            log.warn("queryReadingHistory, bookInfo is empty, bookIds is {}", bookIds);
            return BaseResult.rightReturn(null);
        }

        Map<Integer, BookInfoVO> bookMap = bookList
                .stream()
                .collect(Collectors.toMap(BookInfoVO::getId, bookInfoVO -> bookInfoVO, (v1, v2) -> v2));

        //3. 查询章节信息
        List<Integer> chapIds = readingInfoList
                .stream()
                .map(UserReadingInfoDO::getChapId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        BaseResult<List<ChapterDO>> chapterRes = chapterService.selectByChapIdList(chapIds);
        if (!chapterRes.getSuccess()) {
            log.warn("queryReadingHistory, query chapterInfo error, chapIds is {}, chapterRes {}", chapIds, chapterRes);
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "query chapterInfo error");
        }

        List<ChapterDO> chapList = chapterRes.getData();
        if (CollectionUtils.isEmpty(chapList)) {
            log.warn("queryReadingHistory, chapterInfo is empty, chapIds is {}", chapIds);
            return BaseResult.rightReturn(null);
        }

        Map<Integer, ChapterDO> chapterMap = chapList
                .stream()
                .collect(Collectors.toMap(ChapterDO::getId, chapterDO -> chapterDO, (v1, v2) -> v2));


        //4. 拼装阅读历史信息
        List<ReadingHistoryVO> readingHistoryList = new ArrayList<>();
        for (Integer chapId : chapIds) {
            ChapterDO chapterDO = chapterMap.get(chapId);
            if (chapterDO == null) {
                continue;
            }

            BookInfoVO bookInfoVO = bookMap.get(chapterDO.getBookId());
            if (bookInfoVO == null) {
                continue;
            }
            ReadingHistoryVO vo = new ReadingHistoryVO();
            BeanUtils.copyProperties(chapterDO, vo);
            BeanUtils.copyProperties(bookInfoVO, vo);
            vo.setProgress(bookInfoVO.getChapNum() == 0 ? 0 : (int)Math.ceil(chapterDO.getSequence() / bookInfoVO.getChapNum()) * 100);
            readingHistoryList.add(vo);
        }
        return BaseResult.rightReturn(readingHistoryList);
    }

    @ApiOperation(value="查询图书评分信息", notes="查询图书评分信息")
    @GetMapping(value = "/getBookGrade")
    public BaseResult<BookGradeInfoDO> queryReadingHistory(@RequestParam("bookId") Integer bookId) {
        return gradeInfoService.selectByBookId(bookId);
    }

}
