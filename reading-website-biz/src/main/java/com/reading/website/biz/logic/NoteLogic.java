package com.reading.website.biz.logic;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserNotesInfoDO;
import com.reading.website.api.service.BookService;
import com.reading.website.api.vo.BookInfoVO;
import com.reading.website.api.vo.UserNotesInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 笔记逻辑类
 *
 * @xyang010 2019/6/9
 */
@Component
@Slf4j
public class NoteLogic {

    private final BookService bookService;

    @Autowired
    public NoteLogic(BookService bookService) {
        this.bookService = bookService;
    }

    public List<UserNotesInfoVO> convertDOsToVOs(List<UserNotesInfoDO> notesInfoDOList) {
        List<UserNotesInfoVO> notesInfoVOList = new ArrayList<>();
        for (UserNotesInfoDO notesInfoDO : notesInfoDOList) {
            UserNotesInfoVO userNotesInfoVO = new UserNotesInfoVO();
            BeanUtils.copyProperties(notesInfoDO, userNotesInfoVO);
            BaseResult<BookInfoVO> bookRes = bookService.selectByBookId(notesInfoDO.getBookId());
            if (!bookRes.getSuccess()) {
                log.warn("NoteLogic convertDOsToVOs 查询图书信息失败 bookId {}", notesInfoDO.getBookId());
                notesInfoVOList.add(userNotesInfoVO);
                continue;
            }

            BookInfoVO bookInfoVO = bookRes.getData();
            userNotesInfoVO.setBookName(bookInfoVO.getBookName());
            userNotesInfoVO.setBookPic(bookInfoVO.getBookPic());
            notesInfoVOList.add(userNotesInfoVO);
        }
        return notesInfoVOList;
    }
}
