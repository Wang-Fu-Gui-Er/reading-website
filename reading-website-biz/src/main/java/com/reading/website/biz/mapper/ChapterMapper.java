package com.reading.website.biz.mapper;

import com.reading.website.api.domain.ChapterDO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 章节Mapper
 *
 * @yx8102 2019/3/13
 */
@Component
public interface ChapterMapper {

    /**
     * 批量新增
     * @param list 章节列表
     * @return
     */
    int batchInsert(List<ChapterDO> list);

    /**
     * 批量更新
     * @param list 章节列表
     * @return
     */
    int batchUpdate(@Param(value = "list") List<ChapterDO> list);

    /**
     * 根据图书id查询章节
     * @param bookId 图书id
     * @return
     */
    List<ChapterDO> selectByBookId(@Param(value = "bookId") Integer bookId);

    /**
     * 根据主键查询
     * @param id 主键，章节id
     * @return
     */
    ChapterDO selectByPrimaryKey(Integer id);

    int insertSelective(ChapterDO record);

    int updateByPrimaryKeySelective(ChapterDO record);
}