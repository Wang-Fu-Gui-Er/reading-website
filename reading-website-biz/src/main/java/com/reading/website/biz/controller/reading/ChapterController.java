package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.FileConstant;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;
import com.reading.website.api.service.ChapterService;
import com.reading.website.api.service.UserReadingService;
import com.reading.website.api.vo.ChapterVO;
import com.reading.website.biz.logic.ChapterLogic;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * 章节接口
 *
 * @xyang010 2019/3/13
 */
@Api(value = "章节相关接口", description = "ChapterController", tags = {"章节相关接口"})
@RestController
@Slf4j
@RequestMapping("/chapter")
public class ChapterController {

    private final ChapterService chapterService;

    private final ChapterLogic chapterLogic;

    private final UserReadingService readingService;

    @Autowired
    public ChapterController(ChapterService chapterService, ChapterLogic chapterLogic, UserReadingService readingService) {
        this.chapterService = chapterService;
        this.chapterLogic = chapterLogic;
        this.readingService = readingService;
    }

    @ApiOperation(value="获取图书目录", notes="获取图书目录")
    @GetMapping(value = "/getAllChapter")
    public BaseResult<List<ChapterDO>> getAllChapter(@RequestParam("bookId") Integer bookId, String sort) {
        BaseResult<List<ChapterDO>> chapterRes = chapterService.selectByBookId(bookId, sort);
        List<ChapterDO> dbChapterList = chapterRes.getData();
        for (ChapterDO chapterDO : dbChapterList) {
            String filePath = chapterDO.getContentPath();
            if (!StringUtils.isEmpty(filePath)) {
                filePath = filePath.replaceAll("\\\\", "%5C%5C");
                filePath = filePath.replaceAll(":", "%3A");
                chapterDO.setContentPath(filePath);
            }
        }

        return chapterRes;
    }

    @ApiOperation(value="获取章节信息", notes="获取章节信息")
    @GetMapping(value = "/getChapterInfo")
    public BaseResult<ChapterVO> getChapterInfo(@RequestParam("chapterId") Integer chapterId,
                                                HttpServletRequest request) {

        // 查询章节信息
        BaseResult<ChapterDO> chapterRes = chapterService.selectByChapterId(chapterId);
        if (!chapterRes.getSuccess()) {
            return BaseResult.errorReturn(chapterRes.getCode(), chapterRes.getMessage());
        }

        ChapterDO chapterDO = chapterRes.getData();
        if (chapterDO == null) {
            return BaseResult.rightReturn(chapterLogic.assemblyContent(chapterRes.getData()));
        }

        return BaseResult.rightReturn(chapterLogic.assemblyContent(chapterRes.getData()));
    }

    @ApiOperation(value="更新章节信息", notes="更新章节信息")
    @PostMapping(value = "/updateChapterInfo")
    public BaseResult<Integer> updateChapterInfo(@RequestBody List<ChapterDO> chapterDOList) {
        if (CollectionUtils.isEmpty(chapterDOList)) {
            log.warn("ChapterServiceImpl batchUpdate param chapterDOList is empty");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param chapterDOList is empty");
        }

        List<ChapterDO> insertChapterDOList = new ArrayList<>();
        List<ChapterDO> updateChapterDOList = new ArrayList<>();
        chapterDOList.forEach(chapterDO -> {
            if (chapterDO.getId() == null) {
                insertChapterDOList.add(chapterDO);
            } else {
                updateChapterDOList.add(chapterDO);
            }
        });

        boolean insertRes = chapterLogic.batchInsertChapter(insertChapterDOList);
        if (!insertRes) {
            return BaseResult.errorReturn(null, StatusCodeEnum.LOGIC_ERROR.getCode(), "保存章节信息失败");
        }

        return chapterService.batchUpdate(updateChapterDOList);
    }

    @ApiOperation(value="自动生成章节", notes="自动生成章节")
    @GetMapping(value = "/generatorChapters")
    public BaseResult<List<ChapterDO>> generatorChapters(@RequestParam("bookPath") String bookPath) {
        List<ChapterDO> chapterDOList = FileConstant.generatorChapters(bookPath);
        if (CollectionUtils.isEmpty(chapterDOList)) {
            return BaseResult.errorReturn(StatusCodeEnum.INNER_SERVICE_ERROR.getCode(), "自动生成章节失败");
        }

        return BaseResult.rightReturn(chapterDOList);
    }



}
