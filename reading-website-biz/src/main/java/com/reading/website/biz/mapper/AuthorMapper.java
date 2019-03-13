package com.reading.website.biz.mapper;

import com.reading.website.api.domain.AuthorDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 作者Mapper
 *
 * @yx8102 2019/3/13
 */
@Component
public interface AuthorMapper {

    /**
     * 新增或更新
     * @param authorDO 作者信息
     * @return
     */
    int insertOrUpdate(AuthorDO authorDO);

    /**
     * 根据作者id查询
     * @param authorId 作者id
     * @return
     */
    AuthorDO selectByPrimaryKey(Integer authorId);

    /**
     * 根据作者名称模糊查询
     * @param authorName 作者名称
     * @return
     */
    List<AuthorDO> fuzzySelectByAuthorName(String authorName);
}