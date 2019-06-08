package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.FileConstant;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.service.ChapterService;
import com.reading.website.biz.logic.ChapterLogic;
import com.reading.website.biz.logic.ReadingLogic;
import com.reading.website.biz.utils.Base64Util;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件传输接口：上传，下载
 *
 * @xyang010 2019/3/1
 */
@Api(value = "文件传输接口", description = "TransportController", tags = {"文件传输接口"})
@RestController
@Slf4j
@RequestMapping("/transport")
public class TransportController {

    private final ChapterLogic chapterLogic;

    private final ReadingLogic readingLogic;

    private final ChapterService chapterService;

    @Autowired
    public TransportController(ChapterLogic chapterLogic, ReadingLogic readingLogic, ChapterService chapterService) {
        this.chapterLogic = chapterLogic;
        this.readingLogic = readingLogic;
        this.chapterService = chapterService;
    }

    /**
     * 上传文件接口
     *
     * @param file 文件
     * @param type 上传文件类型
     * @return 文件路径
     */
    @PostMapping("/upload")
    public BaseResult<Map<String, Object>> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        Map<String, Object> resultMap = new HashMap<>();
        if (file.isEmpty()) {
            log.warn("文件为空");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_IS_EMPTY.getCode(), "文件为空");
        }

        if (!(type.equals(FileConstant.BOOK)
                || type.equals(FileConstant.CHAPTER)
                || type.equals(FileConstant.PICTURE))) {
            log.warn("上传请求类型不合法, type {}", type);
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "请求类型不合法");
        }

        String fileType = FileConstant.convert2FileType(file.getContentType());
        if (StringUtils.isEmpty(fileType)) {
            log.warn("文件类型不合法！ fileContentType {}", file.getContentType());
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "文件类型不合法");
        }

        String fileName = FileConstant.generatorFileName(type);
        if (StringUtils.isEmpty(file)) {
            log.warn("生成文件名称失败");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "生成文件名称失败");

        }
        File destFile = new File(  fileName + "." + fileType);

        try {
            file.transferTo(destFile);
            resultMap.put("filePath", destFile.getPath());

            // 图书上传做特判处理
            if (type.equals(FileConstant.BOOK)) {
                resultMap.put("chapterList", FileConstant.generatorChapters(String.valueOf(resultMap.get("filePath"))));
            }
            return BaseResult.rightReturn(resultMap);
        } catch (IOException e) {
            log.warn("上传文件失败");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "上传文件失败");
        }
    }


    /**
     * 文件下载接口
     *
     * @param path 文件路径
     * @return
     */
    @GetMapping("/download")
    public BaseResult<Void> downloadBook(@RequestParam("bookPath") String path,
                                         @RequestParam("bookName") String bookName,
                                         HttpServletResponse response) {
        if (StringUtils.isEmpty(path)) {
            log.warn("文件下载路径为空,bookName {}", bookName);
            return BaseResult.errorReturn(StatusCodeEnum.FILE_PATH_NOT_EXIST.getCode(), "文件下载路径为空");
        }

        File file = new File(path);
        if (!file.exists()) {
            log.warn("文件不存在,bookName {}, path {}", bookName, path);
            return BaseResult.errorReturn(StatusCodeEnum.NOT_FOUND.getCode(), "文件不存在");
        }

        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = null; //文件输入流
        BufferedInputStream bufferedInputStream = null; // 缓冲输入流
        OutputStream outputStream = null; //输出流

        try {
            String fileName = bookName + "." + FileConstant.getFileType(path);
            response.setContentType("application/force-download;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            outputStream = response.getOutputStream();
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            while (bufferedInputStream.read(buffer) != -1) {
                outputStream.write(buffer);
            }

        } catch (Exception e) {
            log.error("下载文件异常，error {}", e);
        }

        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        } catch (IOException e) {
            log.error("关闭文件流异常，error {}", e);
        }
        return BaseResult.rightReturn(null);
    }

    /**
     * 图片展示接口
     *
     * @param path 文件路径
     * @return
     */
    @GetMapping("/getPicture")
    public BaseResult<String> getPicture(@RequestParam("picturePath") String path) {
        if (StringUtils.isEmpty(path)) {
            log.warn("文件路径为空");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_PATH_NOT_EXIST.getCode(), "文件路径为空");
        }

        return BaseResult.rightReturn(Base64Util.fileToBase64ByLocal(path));
    }

    /**
     * 章节预览接口
     *
     * @param contentPath 文件路径
     * @return
     */
    @GetMapping("/getChapterContent")
    public BaseResult<String> getChapterContent(@RequestParam("contentPath") String contentPath,
                                                HttpServletRequest request) {
        if (StringUtils.isEmpty(contentPath)) {
            log.warn("文件路径为空");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_PATH_NOT_EXIST.getCode(), "文件路径为空");
        }

        if (!new File(contentPath).exists()) {
            log.warn("文件不存在");
            return BaseResult.errorReturn(StatusCodeEnum.NOT_FOUND.getCode(), "文件不存在");

        }

        // 若用户已登陆，则更新阅读历史
        LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
        if (loginInfoDTO != null) {
            BaseResult<ChapterDO> chapterRes = chapterService.selectByContentPath(contentPath);
            if (chapterRes.getSuccess() && chapterRes.getData() != null) {
                ChapterDO chapterDO = chapterRes.getData();
                readingLogic.saveReadingInfo(loginInfoDTO.getUserId(), chapterDO.getBookId(), chapterDO.getId());
            }
        }

        return BaseResult.rightReturn(chapterLogic.convertFile2Text(contentPath));
    }
}
