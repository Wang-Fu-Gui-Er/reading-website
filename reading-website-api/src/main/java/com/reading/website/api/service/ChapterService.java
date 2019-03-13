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
     * @return
     */
    BaseResult<List<ChapterDO>> selectByBookId(Integer bookId);

    /**
     * 根据章节id查询
     * @param chapterId 章节id
     * @return
     */
    BaseResult<ChapterDO> selectByChapterId(Integer chapterId);

}
