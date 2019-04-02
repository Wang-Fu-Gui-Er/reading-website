package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookGradeInfoDO;
import com.reading.website.api.service.BookGradeInfoService;
import com.reading.website.biz.mapper.BookGradeInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 图书评分服务实现
 *
 * @xyang010 2019/3/15
 */
@Service
@Slf4j
public class BookGradeInfoServiceImpl implements BookGradeInfoService {

    @Autowired
    private BookGradeInfoMapper bookGradeInfoMapper;

    /**
     * 新增或更新评分信息
     * @param bookGradeInfoDO 评分信息
     * @return
     */
    @Override
    public BaseResult<Integer> insertOrUpdate(BookGradeInfoDO bookGradeInfoDO) {
        if (bookGradeInfoDO == null) {
            log.warn("BookGradeInfoServiceImpl insertOrUpdate param error, param bookGradeInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param bookGradeInfoDO is null");
        }

        try {
            return BaseResult.rightReturn(bookGradeInfoMapper.insertOrUpdate(bookGradeInfoDO));
        } catch (Exception e) {
            log.error("BookGradeInfoServiceImpl insertOrUpdate error, param {}, error {}", bookGradeInfoDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据图书id查询评分信息
     * @param bookId 图书id
     * @return
     */
    @Override
    public BaseResult<BookGradeInfoDO> selectByBookId(Integer bookId) {
        if (bookId == null) {
            log.warn("BookGradeInfoServiceImpl selectByBookId param error, param bookId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param bookId is null");
        }

        try {
            return BaseResult.rightReturn(bookGradeInfoMapper.selectByBookId(bookId));
        } catch (Exception e) {
            log.error("BookGradeInfoServiceImpl selectByBookId error, bookId {}, error {}", bookId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据图书id列表查询评分信息
     * @param bookIds 图书id列表
     * @return
     */
    @Override
    public BaseResult<List<BookGradeInfoDO>> selectByBookIds(List<Integer> bookIds) {
        if (CollectionUtils.isEmpty(bookIds)) {
            log.warn("BookGradeInfoServiceImpl selectByBookIds param error, param bookIds is empty");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param bookIds is empty");
        }

        try {
            return BaseResult.rightReturn(bookGradeInfoMapper.selectByBookIds(bookIds));
        } catch (Exception e) {
            log.error("BookGradeInfoServiceImpl selectByBookIds error, bookIds {}, error {}", bookIds, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
