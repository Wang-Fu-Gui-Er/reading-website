package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.service.ChapterService;
import com.reading.website.biz.logic.ChapterLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ChapterLogic chapterLogic;

    @ApiOperation(value="获取图书目录", notes="获取图书目录")
    @GetMapping(value = "/getAllChapter")
    public BaseResult<List<ChapterDO>> getAllChapter(@RequestParam("bookId") Integer bookId) {
        return chapterService.selectByBookId(bookId);
    }

    @ApiOperation(value="获取章节信息", notes="获取章节信息")
    @PostMapping(value = "/getChapterInfo")
    public BaseResult<ChapterDO> getChapterInfo(@RequestParam("chapterId") Integer chapterId) {
        return chapterService.selectByChapterId(chapterId);
    }

    @ApiOperation(value="新增章节信息", notes="新增章节信息")
    @PostMapping(value = "/insertChapterInfo")
    public BaseResult<Boolean> insertChapterInfo(@RequestBody List<ChapterDO> chapterDOList) {
        boolean res = chapterLogic.batchInsertChapter(chapterDOList);
        if (res) {
            return BaseResult.rightReturn(res);
        }

        return BaseResult.errorReturn(res, StatusCodeEnum.LOGIC_ERROR.getCode(), "保存章节信息失败");
    }

    @ApiOperation(value="更新章节信息", notes="更新章节信息")
    @PostMapping(value = "/updateChapterInfo")
    public BaseResult<Integer> updateChapterInfo(@RequestBody List<ChapterDO> chapterDOList) {
        return chapterService.batchUpdate(chapterDOList);
    }

}
