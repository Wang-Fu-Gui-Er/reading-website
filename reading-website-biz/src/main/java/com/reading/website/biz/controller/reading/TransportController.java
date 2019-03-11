package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.FileConstant;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    /**
     * 上传文件接口
     * @param file 文件
     * @param type 上传文件类型
     * @return 文件路径
     */
    @PostMapping("/upload")
    public BaseResult<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        if (file.isEmpty()) {
            log.warn("文件为空");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_IS_EMPTY.getCode(), "文件为空");
        }

        // 根据类型区分子目录路径
        String filePath = "";
        if (type.equals(FileConstant.PICTURE)) {
            filePath = FileConstant.UPLOAD_PATH + FileConstant.PICTURE_SUB_PATH;

        } else if (type.equals(FileConstant.BOOK)) {
            filePath = FileConstant.UPLOAD_PATH + FileConstant.BOOK_SUB_PATH;

        } else if (type.equals(FileConstant.CHAPTER)) {
            filePath = FileConstant.UPLOAD_PATH + FileConstant.CHAPTER_SUB_PATH;
        }

        String fileName = UUID.randomUUID().toString();
        File destFile = new File(filePath + fileName + file.getContentType());

        try {
            file.transferTo(destFile);
            return BaseResult.rightReturn(destFile.getPath());
        } catch (IOException e) {
            log.warn("上传文件失败");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "上传文件失败");
        }
    }

}
