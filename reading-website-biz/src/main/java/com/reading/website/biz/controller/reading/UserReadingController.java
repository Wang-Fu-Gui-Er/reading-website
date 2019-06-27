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
import com.reading.website.biz.utils.UserUtil;
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

import javax.servlet.http.HttpServletRequest;
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

    private final UserReadingService readingService;

    private final BookService bookService;

    private final ChapterService chapterService;

    private final BookGradeInfoService gradeInfoService;

    @Autowired
    public UserReadingController(UserReadingService readingService, BookService bookService, ChapterService chapterService, BookGradeInfoService gradeInfoService) {
        this.readingService = readingService;
        this.bookService = bookService;
        this.chapterService = chapterService;
        this.gradeInfoService = gradeInfoService;
    }

    @ApiOperation(value="查询阅读历史", notes="查询阅读历史")
    @GetMapping(value = "/history")
    public BaseResult<List<ReadingHistoryVO>> queryReadingHistory(@RequestParam("userId") Integer userId,
                                                                  @RequestParam("pageNum") Integer pageNum,
                                                                  @RequestParam("pageSize") Integer pageSize,
                                                                  @RequestParam(value = "isOnShelf", required = false) Boolean isOnShelf,
                                                                  HttpServletRequest request) {
        if (userId == null) {
            LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
            if (loginInfoDTO != null) {
                userId = loginInfoDTO.getUserId();

            } else {
                return BaseResult.errorReturn(StatusCodeEnum.TOKEN_EXPIRE.getCode(), "TOKEN_EXPIRE");
            }
        }

        //1. 查询阅读记录
        UserReadingInfoQuery query = new UserReadingInfoQuery();
        query.setUserId(userId);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);
        if (isOnShelf != null) {
            query.setIsOnShelf(isOnShelf);
        }

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

        // 无章节处理
        List<ReadingHistoryVO> readingHistoryList = new ArrayList<>();
        List<ChapterDO> chapList = chapterRes.getData();
        if (CollectionUtils.isEmpty(chapList)) {
            log.warn("queryReadingHistory, chapterInfo is empty, chapIds is {}", chapIds);
            bookList.forEach(bookInfoVO -> {
                ReadingHistoryVO vo = new ReadingHistoryVO();
                BeanUtils.copyProperties(bookInfoVO, vo);
                vo.setBookId(bookInfoVO.getId());
                readingHistoryList.add(vo);
            });
            return BaseResult.rightReturn(readingHistoryList, bookRes.getPage());
        }

        Map<Integer, ChapterDO> chapterMap = chapList
                .stream()
                .collect(Collectors.toMap(ChapterDO::getBookId, chapterDO -> chapterDO, (v1, v2) -> v2));


        //4. 拼装阅读历史信息
        for (Integer bookId : bookIds) {
            BookInfoVO bookInfoVO = bookMap.get(bookId);
            if (bookInfoVO == null) {
                continue;
            }

            ReadingHistoryVO vo = new ReadingHistoryVO();
            ChapterDO chapterDO = chapterMap.get(bookId);
            if (chapterDO != null) {
                BeanUtils.copyProperties(chapterDO, vo);
                vo.setProgress(bookInfoVO.getChapNum() == 0 ? 0 : (int)Math.floor((Double.valueOf(chapterDO.getSequence()) / Double.valueOf(bookInfoVO.getChapNum())) * 100));
            } else {
                vo.setProgress(null);
            }

            BeanUtils.copyProperties(bookInfoVO, vo);
            readingHistoryList.add(vo);
        }

        return BaseResult.rightReturn(readingHistoryList, bookRes.getPage());
    }

    @ApiOperation(value="查询图书评分信息", notes="查询图书评分信息")
    @GetMapping(value = "/getBookGrade")
    public BaseResult<BookGradeInfoDO> getBookGrade(@RequestParam("bookId") Integer bookId) {
        return gradeInfoService.selectByBookId(bookId);
    }

    @ApiOperation(value="查询图书是否在用户书架中", notes="查询图书是否在用户书架中")
    @GetMapping(value = "/checkOnShelf")
    public BaseResult<Boolean> checkOnShelf(@RequestParam("userId") Integer userId,
                                            @RequestParam("bookId") Integer bookId,
                                            HttpServletRequest request) {
        if (bookId == null) {
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "参数为空");
        }

        if (userId == null) {
            LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
            if (loginInfoDTO != null) {
                userId = loginInfoDTO.getUserId();

            } else {
                return BaseResult.errorReturn(StatusCodeEnum.TOKEN_EXPIRE.getCode(), "TOKEN_EXPIRE");
            }
        }

        UserReadingInfoQuery query = new UserReadingInfoQuery();
        query.setUserId(userId);
        query.setBookId(bookId);
        query.setPageNum(1);
        query.setPageSize(1);
        BaseResult<List<UserReadingInfoDO>> result = readingService.pageQuery(query);
        if (!result.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "服务异常");
        }

        List<UserReadingInfoDO> list = result.getData();
        return BaseResult.rightReturn(CollectionUtils.isEmpty(list) ? false : list.get(0).getIsOnShelf());
    }

}
