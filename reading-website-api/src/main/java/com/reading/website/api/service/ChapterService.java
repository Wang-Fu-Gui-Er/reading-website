package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.ChapterDO;

import java.util.List;

/**
 * 章节服务
 *
 * @xyang010 2019/3/13
 */
public interface ChapterService {

    /**
     * 根据图书id删除章节
     * @param bookId 图书id
     * @return
     */
    BaseResult<Integer> debByBookId(Integer bookId);

    /**
     * 批量新增
     * @param list 章节列表
     * @return
     */
    BaseResult<Integer> batchInsert(List<ChapterDO> list);

    /**
     * 批量更新
     * @param list 章节列表
     * @return
     */
    BaseResult<Integer> batchUpdate(List<ChapterDO> list);

    /**
     * 根据图书id查询
     * @param bookId 图书id
     * @param sort 排序方式 desc倒序, asc正序
     * @return
     */
    BaseResult<List<ChapterDO>> selectByBookId(Integer bookId, String sort);

    /**
     * 根据路径查询
     * @param contentPath
     * @return
     */
    BaseResult<ChapterDO> selectByContentPath(String contentPath);

    /**
     * 根据章节id查询
     * @param chapterId 章节id
     * @return
     */
    BaseResult<ChapterDO> selectByChapterId(Integer chapterId);

    /**
     * 根据章节id列表查询
     * @param chapIds 章节id列表
     * @return
     */
    BaseResult<List<ChapterDO>> selectByChapIdList(List<Integer> chapIds);


}
