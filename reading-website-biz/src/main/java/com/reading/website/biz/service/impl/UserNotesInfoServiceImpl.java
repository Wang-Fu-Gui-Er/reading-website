package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.UserNotesInfoDO;
import com.reading.website.api.domain.UserNotesInfoQuery;
import com.reading.website.api.service.UserNotesInfoService;
import com.reading.website.biz.mapper.UserNotesInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户笔记服务实现
 *
 * @xyang010 2019/3/18
 */
@Service
@Slf4j
public class UserNotesInfoServiceImpl implements UserNotesInfoService {

    @Autowired
    private UserNotesInfoMapper notesInfoMapper;
    /**
     * 新增笔记
     * @param notesInfoDO 笔记实体
     * @return
     */
    @Override
    public BaseResult<Integer> insert(UserNotesInfoDO notesInfoDO) {
        if (notesInfoDO == null) {
            log.warn("UserNotesInfoServiceImpl insert param error, notesInfoDO is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), " param notesInfoDO is null");
        }

        try {
            return BaseResult.rightReturn(notesInfoMapper.insertSelective(notesInfoDO));
        } catch (Exception e) {
            log.warn("UserNotesInfoServiceImpl insert error, notesInfoDO {}, error {}", notesInfoDO, e);
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 删除笔记
     * @param noteId 笔记id
     * @return
     */
    @Override
    public BaseResult<Integer> del(Integer noteId) {
        if (noteId == null) {
            log.warn("UserNotesInfoServiceImpl del param error, noteId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), " param noteId is null");
        }

        try {
            UserNotesInfoDO notesInfoDO = new UserNotesInfoDO();
            notesInfoDO.setId(noteId);
            notesInfoDO.setIsDeleted(true);
            return BaseResult.rightReturn(notesInfoMapper.updateByPrimaryKeySelective(notesInfoDO));
        } catch (Exception e) {
            log.warn("UserNotesInfoServiceImpl del error, noteId {}, error {}", noteId, e);
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "mapper error");
        }
    }


    /**
     * 查询用户所有读书笔记，每本书一条显示
     * @param query 查询条件
     * userId
     * pageNum
     * pageSize
     * @return
     */
    @Override
    public BaseResult<List<UserNotesInfoDO>> selectNoteByUserId(UserNotesInfoQuery query) {
        if (query == null || query.getUserId() == null) {
            log.warn("UserNotesInfoServiceImpl selectNoteByUserId, param userId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId is null");
        }

        try {
            List<UserNotesInfoDO> notesInfoDOS = notesInfoMapper.selectByUserIdGroupBookId(query.getUserId(),
                                                                                        query.getOffset(),
                                                                                        query.getPageSize());
            Page page = new Page(query.getPageNum(),
                    query.getPageSize(), notesInfoMapper.countSelectByUserIdGroupBookId(query.getUserId()));

            BaseResult<List<UserNotesInfoDO>> result = new BaseResult<>();
            result.setData(notesInfoDOS);
            result.setPage(page);
            return result;

        } catch (Exception e) {
            log.warn("UserNotesInfoServiceImpl selectNoteByUserId error, param {}, error {}", query, e);
            return BaseResult.rightReturn(null);
        }
    }

    /**
     * 查询用户某一本书的读书笔记
     * @param query 查询条件
     * userId
     * bookId
     * pageNum
     * pageSize
     * @return
     */
    @Override
    public BaseResult<List<UserNotesInfoDO>> selectNoteByUserIdAndBookId(UserNotesInfoQuery query) {
        if (query == null || query.getUserId() == null || query.getBookId() == null) {
            log.warn("UserNotesInfoServiceImpl selectNoteByUserIdAndBookId, param query is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "query param is null");
        }

        try {
            List<UserNotesInfoDO> notesInfoDOS = notesInfoMapper.pageQuery(query);
            Page page = new Page(query.getPageNum(), query.getPageSize(), notesInfoMapper.countPageQuery(query));

            BaseResult<List<UserNotesInfoDO>> result = new BaseResult<>();
            result.setData(notesInfoDOS);
            result.setPage(page);
            return result;

        } catch (Exception e) {
            log.warn("UserNotesInfoServiceImpl selectNoteByUserIdAndBookId error, param {}, error {}", query, e);
            return BaseResult.rightReturn(null);
        }
    }

}
