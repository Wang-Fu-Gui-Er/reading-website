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
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        List<ChapterDO> chapterDOList = new ArrayList<>();

        try {
            File file = new File(bookPath);
            if (!file.isFile()) {
                log.warn("文件非法");
                return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "非法文件");
            }

            if (!file.exists()) {
                log.warn("文件不存在");
                return BaseResult.errorReturn(StatusCodeEnum.NOT_FOUND.getCode(), "文件不存在");
            }

            // 编码格式
            String encoding = "GBK";
            // 输入流
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int chapNum = 0;
            boolean bflag=false;
            int n=0;
            String newStr=null;
            String titleName=null;
            List<String> chapterNameList = new ArrayList<>();
            int chapNameIndex = 0;
            String newChapterName = null;//新章节名称
            String substring=null;
            int indexOf=0;
            int indexOf1=0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                // 正则匹配章节标题
                Pattern p = Pattern.compile("(^\\s*第*)([0123456789零一二三四五六七八九十百千万]{1,})([章节卷集部篇、]{1,})(\\s*)(.*)($\\s*)");

                Matcher matcher = p.matcher(lineTxt);
                Matcher matcher1 = p.matcher(lineTxt);
                newStr= newStr + lineTxt;   //已读文本

                ChapterDO chapterDO = new ChapterDO();
                while (matcher.find()) {
                    //章节去空
                    newChapterName = matcher.group().trim();
                    chapterNameList.add(newChapterName);
                    chapNameIndex = chapterNameList.size() - 1;
                    indexOf1=indexOf;
                    indexOf = newStr.indexOf(newChapterName);
                    if(bflag) {
                        bflag=false;
                        break;
                    }
                    if(n==0) {
                        indexOf1 = newStr.indexOf(newChapterName);
                    }
                    n=1;
                    bflag=true;
                }
                while(matcher1.find()) {
                    if(indexOf!=indexOf1) {
                        chapNum++;

                        substring = newStr.substring(indexOf1, indexOf);
                        System.out.println(substring);
                        // 写入文件
                        String chapterPath = generatorChapterFile(substring, FileConstant.getFileType(bookPath));
                        chapterDO.setTitle(chapterNameList.get(chapNameIndex - 1));
                        chapterDO.setSequence(chapNum);
                        chapterDO.setContentPath(chapterPath);
                        chapterDOList.add(chapterDO);
                    }

                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            log.warn("自动生成文章异常, error {}", e);

        }
        return BaseResult.rightReturn(chapterDOList);
    }

    /**
     * 生成章节文件
     * @param chapContent
     * @param fileType
     */
    public String generatorChapterFile(String chapContent, String fileType) {
        String pathName = "";
        try {
            pathName = FileConstant.generatorFileName(FileConstant.CHAPTER) + "." + fileType;
            File file = new File(pathName);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println(chapContent);
        } catch (FileNotFoundException e) {
            log.warn("自定生成文章失败 fileType {}, pathName {}", fileType, pathName);
            return null;
        }
        return pathName;
    }

}
