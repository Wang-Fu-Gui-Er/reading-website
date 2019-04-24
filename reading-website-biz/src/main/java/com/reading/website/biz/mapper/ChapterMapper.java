package com.reading.website.biz.mapper;

import com.reading.website.api.domain.ChapterDO;
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
     * @param sort 排序方式 desc倒序, asc正序
     * @return
     */
    List<ChapterDO> selectByBookId(@Param(value = "bookId") Integer bookId, @Param("sort") String sort);

    /**
     * 根据主键查询
     * @param id 主键，章节id
     * @return
     */
    ChapterDO selectByPrimaryKey(Integer id);

    /**
     * 根据章节id列表查询
     * @param chapIds 章节id列表
     * @return
     */
    List<ChapterDO> selectByChapIdList(List<Integer> chapIds);

}