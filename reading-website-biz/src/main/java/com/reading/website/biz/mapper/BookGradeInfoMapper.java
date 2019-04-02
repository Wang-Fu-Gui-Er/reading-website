package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookGradeInfoDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图书评分信息Mapper
 *
 * @yx8102 2019/3/15
 */
@Component
public interface BookGradeInfoMapper {

    /**
     * 新增或更新评分信息
     * @param bookGradeInfoDO 评分信息
     * @return
     */
    int insertOrUpdate(BookGradeInfoDO bookGradeInfoDO);

    /**
     * 根据图书id查询评分信息
     * @param bookId 图书id
     * @return
     */
    BookGradeInfoDO selectByBookId(Integer bookId);

    /**
     * 根据图书id列表查询评分信息
     * @param bookIds 图书id列表
     * @return
     */
    List<BookGradeInfoDO> selectByBookIds(List<Integer> bookIds);
}