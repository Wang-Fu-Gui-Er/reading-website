package com.reading.website.api.constants;

import com.reading.website.api.domain.ChapterDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件相关常量类
 *
 * @xyang010 2019/3/8
 */
@Slf4j
public class FileConstant {
    // 上传统一路径
//    public static final String UPLOAD_PATH = "C:\\Users\\Administrator\\IdeaProjects\\reading-website\\reading-website-biz\\src\\main\\resources\\upload";
    public static final String UPLOAD_PATH = "/Users/xyang010/Documents/IdeaProject/reading-website/reading-website-biz/src/main/resources/upload";

    // 图片子路径
    public static final String PICTURE_SUB_PATH = File.separator + "picture";

    // 整本书子路径
    public static final String BOOK_SUB_PATH = File.separator + "books";

    // 章节子路径
    public static final String CHAPTER_SUB_PATH = File.separator + "chapters";

    // 图片类型
    public static final String FILE_TYPE_JPG = "jpg";
    public static final String FILE_TYPE_GIF = "gif";
    public static final String FILE_TYPE_PNG = "png";
    public static final String FILE_TYPE_BMP = "bmp";

    // 上传文件类型
    // 图书
    public static final String BOOK = "book";

    // 章节
    public static final String CHAPTER = "chapter";

    // 图片
    public static final String PICTURE = "picture";

    /**
     * 获取文件类型
     *
     * @param path 文件全路径
     * @return 文件类型
     */
    public static String getFileType(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    /**
     * 转换文件类型为后缀
     *
     * @param fileContentType 文件内容类型
     * @return
     */
    public static String convert2FileType(String fileContentType) {
        if (StringUtils.isEmpty(fileContentType)) {
            return null;
        }

        switch (fileContentType) {
            case "image/jpeg":
                return "jpeg";
            case "image/gif":
                return "gif";
            case "image/x-ms-bmp":
                return "bmp";
            case "image/png":
                return "png";
            case "text/plain":
                return "txt";
            case "application/pdf":
                return "pdf";
            default:
                return null;
        }
    }

    /**
     * 转换文件后缀为类型
     *
     * @param fileType 文件后缀
     * @return
     */
    public static String convert2ContentType(String fileType) {
        if (StringUtils.isEmpty(fileType)) {
            return null;
        }

        switch (fileType) {
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            case "bmp":
                return "image/x-ms-bmp";
            case "png":
                return "image/png";
            case "txt":
                return "text/plain";
            case "pdf":
                return "application/pdf";
            default:
                return null;
        }
    }

    /**
     * 生成文件名称（无后缀即文件类型）
     *
     * @param type 文件业务类型：picture,book,chapter
     * @return
     */
    public static String generatorFileName(String type) {
        String filePath = "";
        if (type.equals(FileConstant.PICTURE)) {
            filePath = FileConstant.UPLOAD_PATH + FileConstant.PICTURE_SUB_PATH;

        } else if (type.equals(FileConstant.BOOK)) {
            filePath = FileConstant.UPLOAD_PATH + FileConstant.BOOK_SUB_PATH;

        } else if (type.equals(FileConstant.CHAPTER)) {
            filePath = FileConstant.UPLOAD_PATH + FileConstant.CHAPTER_SUB_PATH;
        }

        String fileName = UUID.randomUUID().toString();
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return filePath + File.separator + fileName;
    }

    /**
     * 自动生成章节
     *
     * @param bookPath 图书地址
     * @return
     */
    public static List<ChapterDO> generatorChapters(String bookPath) {

        List<ChapterDO> chapterDOList = new ArrayList<>();

        try {
            File file = new File(bookPath);
            if (!file.isFile()) {
                log.warn("文件非法");
                return chapterDOList;
            }

            if (!file.exists()) {
                log.warn("文件不存在");
                return chapterDOList;
            }

            // 编码格式
            String encoding = "GBK";
            // 输入流
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            int chapNum = 0;
            boolean bflag = false;
            int n = 0;
            String newStr = null;
            String titleName = null;
            List<String> chapterNameList = new ArrayList<>();
            int chapNameIndex = 0;
            String newChapterName = null;//新章节名称
            String substring = null;
            int indexOf = 0;
            int indexOf1 = 0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                // 正则匹配章节标题
                Pattern p = Pattern.compile("(^\\s*第*)([0123456789零一二三四五六七八九十百千万]{1,})([章节卷集部篇、]{1,})(\\s*)(.*)($\\s*)");

                Matcher matcher = p.matcher(lineTxt);
                Matcher matcher1 = p.matcher(lineTxt);
                newStr = newStr + lineTxt;   //已读文本

                ChapterDO chapterDO = new ChapterDO();
                while (matcher.find()) {
                    //章节去空
                    newChapterName = matcher.group().trim();
                    chapterNameList.add(newChapterName);
                    chapNameIndex = chapterNameList.size() - 1;
                    indexOf1 = indexOf;
                    indexOf = newStr.indexOf(newChapterName);
                    if (bflag) {
                        bflag = false;
                        break;
                    }
                    if (n == 0) {
                        indexOf1 = newStr.indexOf(newChapterName);
                    }
                    n = 1;
                    bflag = true;
                }
                while (matcher1.find()) {
                    if (indexOf != indexOf1) {
                        chapNum++;

                        substring = newStr.substring(indexOf1, indexOf);

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
        return chapterDOList;
    }

    /**
     * 生成章节文件
     *
     * @param chapContent
     * @param fileType
     */
    public static String generatorChapterFile(String chapContent, String fileType) {
        String pathName = "";
        try {
            pathName = FileConstant.generatorFileName(FileConstant.CHAPTER) + "." + fileType;
            File file = new File(pathName);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println(chapContent);
        } catch (FileNotFoundException e) {
            log.warn("自动生成文章失败 fileType {}, pathName {}", fileType, pathName);
            return null;
        }
        return pathName;
    }
}
