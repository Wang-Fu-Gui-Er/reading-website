package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserNotesInfoDO;
import com.reading.website.api.domain.UserNotesInfoQuery;

import java.util.List;

/**
 * 用户笔记服务
 *
 * @xyang010 2019/3/18
 */
public interface UserNotesInfoService {

    /**
     * 新增笔记
     * @param notesInfoDO 笔记实体
     * @return
     */
    BaseResult<Integer> insert(UserNotesInfoDO notesInfoDO);

    /**
     * 删除笔记
     * @param noteId 笔记id
     * @return
     */
    BaseResult<Integer> del(Integer noteId);

    /**
     * 查询用户所有读书笔记，每本书显示一条
     * @param query 查询条件
     * userId
     * pageNum
     * pageSize
     * @return
     */
    BaseResult<List<UserNotesInfoDO>> selectNoteByUserId(UserNotesInfoQuery query);

    /**
     * 查询用户某一本书的读书笔记
     * @param query 查询条件
     * userId
     * bookId
     * pageNum
     * pageSize
     * @return
     */
    BaseResult<List<UserNotesInfoDO>> selectNoteByUserIdAndBookId(UserNotesInfoQuery query);

}
