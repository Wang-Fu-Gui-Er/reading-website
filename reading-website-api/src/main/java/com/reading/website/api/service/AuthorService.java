package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.AuthorDO;
import com.reading.website.api.vo.AuthorVO;

import java.util.List;

/**
 * 作者相关服务
 *
 * @xyang010 2019/3/13
 */
public interface AuthorService {

    /**
     * 新增或更新
     * @param authorDO 作者信息
     * @return
     */
    BaseResult<Integer> insertOrUpdate(AuthorDO authorDO);

    /**
     * 根据作者id查询
     * @param authorId 作者id
     * @return
     */
    BaseResult<AuthorVO> selectByAuthorId(Integer authorId);

    /**
     * 根据作者名称模糊查询
     * @param authorName 作者名称
     * @return
     */
    BaseResult<List<AuthorVO>> fuzzySelectByAuthorName(String authorName);

    /**
     * 删除作者信息
     * @param authorId 作者id
     * @return
     */
    BaseResult<Integer> delByAuthorId(Integer authorId);
}
