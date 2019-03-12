package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BookDO;
import com.reading.website.api.domain.BookInfoQuery;
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
     * 查询最新的书籍，max 50
     * @return
     */
    List<BookDO> listNewlyBooks();

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
}