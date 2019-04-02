package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.FileConstant;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Objects;
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
        String fileType = FileConstant.convertFileType(file.getContentType());
        if (StringUtils.isEmpty(fileType)) {
            log.warn("文件类型不合法！ fileContentType {}", file.getContentType());
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "文件类型不合法");
        }
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File destFile = new File(filePath + File.separator + fileName + "." + fileType);

        try {
            file.transferTo(destFile);
            return BaseResult.rightReturn(destFile.getPath());
        } catch (IOException e) {
            log.warn("上传文件失败");
            return BaseResult.errorReturn(StatusCodeEnum.FILE_UPLOAD_ERROR.getCode(), "上传文件失败");
        }
    }


    /**
     * 文件下载接口
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
            String fileName  = bookName + FileConstant.getFileType(path);
            response.setContentType("application/force-download;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));

            outputStream = response.getOutputStream();
            fileInputStream = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            while(bufferedInputStream.read(buffer) != -1){
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

}
