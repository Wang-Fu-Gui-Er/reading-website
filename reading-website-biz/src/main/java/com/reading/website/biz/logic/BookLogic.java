package com.reading.website.biz.logic;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookGradeInfoDO;
import com.reading.website.api.service.BookGradeInfoService;
import com.reading.website.api.service.BookService;
import com.reading.website.api.vo.BookInfoVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图书逻辑处理类
 *
 * @xyang010 2019/4/1
 */
@Component
@Slf4j
public class BookLogic {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookGradeInfoService gradeInfoService;


    /**
     * 拼装图书评分信息
     * @param bookDOList 图书列表
     */
    public List<BookInfoVO> assemblyGrade(List<BookDO> bookDOList) {
        if (CollectionUtils.isEmpty(bookDOList)) {
            return null;
        }

        // 查询评分信息
        List<Integer> bookIds = bookDOList.stream().map(BookDO::getId).collect(Collectors.toList());
        BaseResult<List<BookGradeInfoDO>> gradeRes = gradeInfoService.selectByBookIds(bookIds);
        if (!gradeRes.getSuccess()) {
            log.warn("gradeInfoService selectByBookIds error, result {}", gradeRes);
            return null;
        }

        Map<Integer, BookGradeInfoDO> bookGradeMap = gradeRes
                .getData()
                .stream()
                .collect(Collectors.toMap(BookGradeInfoDO::getBookId, gradeInfoDO -> gradeInfoDO, (v1, v2) -> v2));

        // 拼装展示信息
        List<BookInfoVO> bookInfoVOList = new ArrayList<>(bookDOList.size());
        bookDOList.forEach(bookDO -> {
            BookInfoVO bookInfoVO = new BookInfoVO();
            BeanUtils.copyProperties(bookDO, bookInfoVO);
            BookGradeInfoDO gradeInfoDO = bookGradeMap.get(bookDO.getId());
            if (gradeInfoDO != null) {
                bookInfoVO.setAvgScore(gradeInfoDO.getAvgScore());
            }
            bookInfoVOList.add(bookInfoVO);

        });

        return bookInfoVOList;
    }
}
