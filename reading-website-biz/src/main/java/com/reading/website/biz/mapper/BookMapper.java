package com.reading.website.biz.mapper;

import com.reading.website.api.base.Page;
import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图书Mapper
 *
 * @yx8102 2019/1/21
 */
@Component
public interface BookMapper {

    /**
     * 分页查询
     * @param query
     * @return
     */
    List<BookDO> pageQuery(BookInfoQuery query);

    /**
     * 条件查询
     * @param query
     * @return
     */
    List<BookDO> selectSelective(BookInfoQuery query);

    /**
     * 增加或更新
     * @param record
     * @return
     */
    int insertOrUpdate(BookDO record);

    /**
     * 批量增加
     * @param list
     * @return
     */
    int batchInsert(List<BookDO> list);

    /**
     * 计数
     * @param query 查询条件
     * @return
     */
    int countSelective(BookInfoQuery query);

    /**
     * 查询最新的图书
     * @param page 分页信息
     * @return
     */
    List<BookDO> listNewlyBooks(Page page);

    /**
     * 查询最受欢迎的图书
     * @param page 分页信息
     * @return
     */
    List<BookDO> listFavorableBooks(Page page);

    /**
     * 查询经典图书
     * @param page 分页信息
     * @return
     */
    List<BookDO> listClassicBooks(Page page);

    /**
     * 查找相似图书列表(随机返回同类中的最多两个)
     * @param smallCateId 小类id
     * @return
     */
    List<BookDO> listSimilarBook(@Param("smallCateId") Integer smallCateId);


}