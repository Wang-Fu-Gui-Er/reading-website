package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.FileConstant;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.service.ChapterService;
import com.reading.website.api.vo.ChapterVO;
import com.reading.website.biz.logic.ChapterLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
    public BaseResult<List<ChapterDO>> getAllChapter(@RequestParam("bookId") Integer bookId, String sort) {
        return chapterService.selectByBookId(bookId, sort);
    }

    @ApiOperation(value="获取章节信息", notes="获取章节信息")
    @PostMapping(value = "/getChapterInfo")
    public BaseResult<ChapterVO> getChapterInfo(@RequestParam("chapterId") Integer chapterId) {
        BaseResult<ChapterDO> chapterRes = chapterService.selectByChapterId(chapterId);
        if (!chapterRes.getSuccess()) {
            return BaseResult.errorReturn(chapterRes.getCode(), chapterRes.getMessage());
        }

        return BaseResult.rightReturn(chapterLogic.assemblyContent(chapterRes.getData()));
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
