package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据评论id修改点赞数
     * @param id 评论id
     * @param isAdd 是否点赞
     * @return
     */
    int updateLikeNum(@Param("id") Integer id , @Param("isAdd") Boolean isAdd);
}