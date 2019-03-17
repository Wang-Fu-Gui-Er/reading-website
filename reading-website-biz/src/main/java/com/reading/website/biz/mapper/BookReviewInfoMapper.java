package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图书评价信息Mapper
 *
 * @yx8102 2019/3/13
 */
@Component
public interface BookReviewInfoMapper {

    /**
     * 新增或更新
     * @param reviewInfoDO
     * @return
     */
    int insertOrUpdate(BookReviewInfoDO reviewInfoDO);

    /**
     * 分页查询
     * @param query
     * @return
     */
    List<BookReviewInfoDO> pageQuery(BookReviewInfoQuery query);


    /**
     * 计数
     * @param query
     */
    int count(BookReviewInfoQuery query);
}