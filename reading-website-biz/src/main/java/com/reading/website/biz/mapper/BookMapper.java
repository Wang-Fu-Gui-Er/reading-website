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
     * 条件查询，可分页
     * @param query
     * @return
     */
    List<BookDO> selectSelective(BookInfoQuery query);

    int deleteByPrimaryKey(Integer id);

    int insert(BookDO record);

    int insertSelective(BookDO record);

    BookDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BookDO record);

    int updateByPrimaryKey(BookDO record);
}