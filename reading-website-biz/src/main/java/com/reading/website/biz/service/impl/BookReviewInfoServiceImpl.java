package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookReviewInfoDO;
import com.reading.website.api.domain.BookReviewInfoQuery;
import com.reading.website.api.service.BookReviewInfoService;
import com.reading.website.biz.mapper.BookReviewInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图书评论服务实现
 *
 * @xyang010 2019/3/17
 */
@Service
@Slf4j
public class BookReviewInfoServiceImpl implements BookReviewInfoService {

    @Autowired
    private BookReviewInfoMapper reviewInfoMapper;

    /**
     * 新增或更新
     * @param reviewInfoDO 评论实体
     * @return
     */
    @Override
    public BaseResult<Integer> insertOrUpdate(BookReviewInfoDO reviewInfoDO) {
        if (reviewInfoDO == null) {
            log.warn("BookReviewInfoServiceImpl insertOrUpdate param error, reviewInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param reviewInfoDO is null");
        }

        try {
            return BaseResult.rightReturn(reviewInfoMapper.insertOrUpdate(reviewInfoDO));

        } catch (Exception e) {
            log.error("BookReviewInfoServiceImpl insertOrUpdate error, param reviewInfoDO {}, error {}", reviewInfoDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 分页查询
     * @param query 查询条件
     * 默认时间倒序
     * @return
     */
    @Override
    public BaseResult<List<BookReviewInfoDO>> pageQuery(BookReviewInfoQuery query) {
        if (query == null) {
            log.warn("BookReviewInfoServiceImpl pageQuery param error, query is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param query is null");
        }

        try {
            BaseResult<List<BookReviewInfoDO>> result = new BaseResult<>();
            result.setSuccess(true);
            result.setData(reviewInfoMapper.pageQuery(query));
            Page page = new Page(query.getPageNum(), query.getPageSize(), reviewInfoMapper.count(query));
            result.setPage(page);
            return result;

        } catch (Exception e) {
            log.error("BookReviewInfoServiceImpl pageQuery error, param query {}, error {}", query, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
