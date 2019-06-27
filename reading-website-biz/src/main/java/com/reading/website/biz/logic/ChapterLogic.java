package com.reading.website.biz.logic;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.service.BookService;
import com.reading.website.api.service.ChapterService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.api.vo.ChapterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 章节逻辑类
 *
 * @xyang010 2019/4/13
 */
@Component
@Slf4j
public class ChapterLogic {
    private final ChapterService chapterService;

    private final BookService bookService;


    @Autowired
    public ChapterLogic(ChapterService chapterService, BookService bookService) {
        this.chapterService = chapterService;
        this.bookService = bookService;
    }

    /**
     * 批量新增章节信息
     * @param chapterDOList
     * @return
     */
    public boolean batchInsertChapter(List<ChapterDO> chapterDOList) {
        if (CollectionUtils.isEmpty(chapterDOList)) {
            return Boolean.TRUE;
        }
        BaseResult<Integer> chapterRes = chapterService.batchInsert(chapterDOList);
        Integer bookId = chapterDOList.get(0).getBookId();
        BaseResult<BookInfoVO> bookRes = bookService.selectByBookId(bookId);
        if (!bookRes.getSuccess()) {
            log.warn("查询图书信息失败, bookId {}, bookRes {}", bookId, bookRes);
            return false;
        }

        if (bookRes.getData() == null) {
            log.warn("图书不存在, bookId {}, bookRes {}", bookId, bookRes);
            return false;
        }

        if (!chapterRes.getSuccess()) {
            log.warn("批量保存章节信息失败 chapterRes {}", chapterRes);
            return false;
        }

        BookDO bookDO = new BookDO();
        bookDO.setId(bookId);
        bookDO.setChapNum(bookRes.getData().getChapNum() + chapterRes.getData());
        BaseResult<Integer> updateBookRes = bookService.insertOrUpdateBook(bookDO);
        if (!updateBookRes.getSuccess()) {
            log.warn("更新图书章节数量失败, bookId {}, beforeChapNum {}, afterChapNum {}", bookId, bookRes.getData().getChapNum(), bookDO.getChapNum());
        }
        return true;
    }

    public boolean batchUpdateChapter(List<ChapterDO> chapterDOList) {
        if (CollectionUtils.isEmpty(chapterDOList)) {
            return Boolean.TRUE;
        }
        Integer bookId = chapterDOList.get(0).getBookId();
        BaseResult<BookInfoVO> bookRes = bookService.selectByBookId(bookId);
        if (!bookRes.getSuccess()) {
            log.warn("查询图书信息失败, bookId {}, bookRes {}", bookId, bookRes);
            return false;
        }

        if (bookRes.getData() == null) {
            log.warn("图书不存在, bookId {}, bookRes {}", bookId, bookRes);
            return false;
        }

        BaseResult<Integer> updateRes = chapterService.batchUpdate(chapterDOList);
        if (!updateRes.getSuccess()) {
            log.warn("批量更新章节信息失败 updateRes {}", updateRes);
            return false;
        }

        Long delChapNum = chapterDOList.stream().filter(chapterDO -> chapterDO.getIsDeleted().equals(Boolean.TRUE)).count();
        if (delChapNum != 0) {
            BookDO bookDO = new BookDO();
            bookDO.setId(bookId);
            bookDO.setChapNum(bookRes.getData().getChapNum() - delChapNum.intValue());
            BaseResult<Integer> updateBookRes = bookService.insertOrUpdateBook(bookDO);
            if (!updateBookRes.getSuccess()) {
                log.warn("更新图书章节数量失败, bookId {}, beforeChapNum {}, afterChapNum {}", bookId, bookRes.getData().getChapNum(), bookDO.getChapNum());
            }
        }

        return true;
    }


        /**
         * 补充章节内容
         * @param chapterDO
         * @return
         */
    public ChapterVO assemblyContent(ChapterDO chapterDO) {
        if (chapterDO == null) {
            return null;
        }

        String content = convertFile2Text(chapterDO.getContentPath());
        ChapterVO chapterVO = new ChapterVO();
        BeanUtils.copyProperties(chapterDO, chapterVO);
        chapterVO.setContent(content);
        return chapterVO;
    }

    public String convertFile2Text(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }


        byte[] dataBytes;

        try(InputStream inputStream = new FileInputStream(filePath)) {
            dataBytes = new byte[inputStream.available()];
            inputStream.read(dataBytes);
        } catch (IOException e) {
            log.error("获取章节内容失败 error {}", e);
            return null;
        }

        return new String(dataBytes);
    }

}
