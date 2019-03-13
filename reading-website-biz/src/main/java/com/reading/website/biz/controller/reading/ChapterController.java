package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value="获取图书目录", notes="获取图书目录")
    @GetMapping(value = "/getAllChapter")
    public BaseResult<List<ChapterDO>> getAllChapter(@RequestParam("bookId") Integer bookId) {
        return chapterService.selectByBookId(bookId);
    }

    @ApiOperation(value="获取章节信息", notes="获取章节信息")
    @GetMapping(value = "/getChapterInfo")
    public BaseResult<ChapterDO> getChapterInfo(@RequestParam("chapterId") Integer chapterId) {
        return chapterService.selectByChapterId(chapterId);
    }



}
