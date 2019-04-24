package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.ChapterDO;
import com.reading.website.api.service.ChapterService;
import com.reading.website.biz.enums.ChapterSortEnum;
import com.reading.website.biz.mapper.ChapterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 章节服务实现类
 *
 * @xyang010 2019/3/13
 */
@Service
@Slf4j
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    /**
     * 批量新增
     * @param list 章节列表
     * @return
     */
    @Override
    public BaseResult<Integer> batchInsert(List<ChapterDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            log.warn("ChapterServiceImpl batchInsert param chapterDOList is empty");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param chapterDOList is empty");
        }

        try {
            return BaseResult.rightReturn(chapterMapper.batchInsert(list));
        } catch (Exception e) {
            log.error("ChapterServiceImpl batchInsert error, chapterDOList {}, error{}", list, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 批量更新
     * @param list 章节列表
     * @return
     */
    @Override
    public BaseResult<Integer> batchUpdate(List<ChapterDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            log.warn("ChapterServiceImpl batchUpdate param chapterDOList is empty");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param chapterDOList is empty");
        }

        try {
            return BaseResult.rightReturn(chapterMapper.batchUpdate(list));
        } catch (Exception e) {
            log.error("ChapterServiceImpl batchUpdate error, chapterDOList {}, error{}", list, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据图书id查询
     * @param bookId 图书id
     * @param sort 排序方式 desc倒序, asc正序
     * @return 章节列表
     */
    @Override
    public BaseResult<List<ChapterDO>> selectByBookId(Integer bookId, String sort) {
        if (bookId == null) {
            log.warn("ChapterServiceImpl selectByBookId param selectByBookId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param selectByBookId is null");
        }

        try {
            if (StringUtils.isEmpty(sort) || !sort.equals(ChapterSortEnum.DESC.getType())) {
                sort = ChapterSortEnum.ASC.getType();
            }
            return BaseResult.rightReturn(chapterMapper.selectByBookId(bookId, sort));
        } catch (Exception e) {
            log.error("ChapterServiceImpl selectByBookId error, bookId {}, error{}", bookId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据章节id查询
     * @param chapterId 章节id
     * @return
     */
    @Override
    public BaseResult<ChapterDO> selectByChapterId(Integer chapterId) {
        if (chapterId == null) {
            log.warn("ChapterServiceImpl selectByChapterId param chapterId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param chapterId is null");
        }

        try {
            return BaseResult.rightReturn(chapterMapper.selectByPrimaryKey(chapterId));
        } catch (Exception e) {
            log.error("ChapterServiceImpl selectByChapterId error, chapterId {}, error{}", chapterId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据章节id列表查询
     * @param chapIds 章节id列表
     * @return
     */
    @Override
    public BaseResult<List<ChapterDO>> selectByChapIdList(List<Integer> chapIds) {
        if (CollectionUtils.isEmpty(chapIds)) {
            log.warn("ChapterServiceImpl selectByChapIdList param chapIds is empty");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param chapIds is empty");
        }

        try {
            return BaseResult.rightReturn(chapterMapper.selectByChapIdList(chapIds));
        } catch (Exception e) {
            log.error("ChapterServiceImpl selectByChapIdList error, chapIds {}, error{}", chapIds, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
