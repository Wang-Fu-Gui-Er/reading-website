package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.BookGradeInfoDO;

import java.util.List;

/**
 * 图书评分服务
 *
 * @xyang010 2019/3/15
 */
public interface BookGradeInfoService {

    /**
     * 新增或更新评分信息
     * @param bookGradeInfoDO 评分信息
     * @return
     */
    BaseResult<Integer> insertOrUpdate(BookGradeInfoDO bookGradeInfoDO);

    /**
     * 根据图书id查询评分信息
     * @param bookId 图书id
     * @return
     */
    BaseResult<BookGradeInfoDO> selectByBookId(Integer bookId);

    /**
     * 根据图书id列表查询评分信息
     * @param bookIds 图书id列表
     * @return
     */
    BaseResult<List<BookGradeInfoDO>> selectByBookIds(List<Integer> bookIds);

}
