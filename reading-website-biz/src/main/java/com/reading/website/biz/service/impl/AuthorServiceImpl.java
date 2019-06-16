package com.reading.website.biz.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.constants.FileConstant;
import com.reading.website.api.domain.AuthorDO;
import com.reading.website.api.service.AuthorService;
import com.reading.website.api.service.BookService;
import com.reading.website.api.vo.AuthorVO;
import com.reading.website.biz.logic.AuthorLogic;
import com.reading.website.biz.mapper.AuthorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 作者服务实现类
 *
 * @xyang010 2019/3/13
 */
@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorMapper authorMapper;

    /**
     * 新增或更新
     * @param authorDO 作者信息
     * @return
     */
    @Override
    public BaseResult<Integer> insertOrUpdate(AuthorDO authorDO) {
        if (authorDO == null) {
            log.warn("AuthorServiceImpl insertOrUpdate param authorDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param authorDO is null");
        }

        try {
            String authorPic = authorDO.getAuthorPic();
            if (!StringUtils.isEmpty(authorPic) && !authorPic.contains(FileConstant.UPLOAD_PATH)) {
                authorDO.setAuthorPic(null);
            }
            return BaseResult.rightReturn(authorMapper.insertOrUpdate(authorDO));
        } catch (Exception e) {
            log.error("AuthorServiceImpl insertOrUpdate failed, authorDO {}, error {},", authorDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据作者id查询
     * @param authorId 作者id
     * @return
     */
    @Override
    public BaseResult<AuthorVO> selectByAuthorId(Integer authorId) {
        if (authorId == null) {
            log.warn("AuthorServiceImpl selectByAuthorId param authorId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param authorId is null");
        }

        try {
            return BaseResult.rightReturn(AuthorLogic.convertDO2VO(authorMapper.selectByPrimaryKey(authorId)));
        } catch (Exception e) {
            log.error("AuthorServiceImpl selectByAuthorId failed, authorId {}, error {},", authorId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据作者名称模糊查询
     * @param authorName 作者名称
     * @return
     */
    @Override
    public BaseResult<List<AuthorVO>> fuzzySelectByAuthorName(String authorName) {
        try {
            return BaseResult.rightReturn(AuthorLogic.convertDOs2VOs(authorMapper.fuzzySelectByAuthorName(authorName)));
        } catch (Exception e) {
            log.error("AuthorServiceImpl fuzzySelectByAuthorName failed, authorName {}, error {},", authorName, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 删除作者信息
     * @param authorId 作者id
     * @return
     */
    @Override
    public BaseResult<Integer> delByAuthorId(Integer authorId) {
        if (authorId == null) {
            log.warn("AuthorServiceImpl delByAuthorId param authorId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param authorId is null");
        }

        try {
            AuthorDO authorDO = new AuthorDO();
            authorDO.setId(authorId);
            authorDO.setIsDeleted(true);
            return BaseResult.rightReturn(authorMapper.insertOrUpdate(authorDO));
        } catch (Exception e) {
            log.error("AuthorServiceImpl insertOrUpdate failed, authorId {}, error {},", authorId, e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }
}
