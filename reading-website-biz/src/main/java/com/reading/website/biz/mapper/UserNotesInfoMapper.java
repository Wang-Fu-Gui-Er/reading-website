package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserNotesInfoDO;
import com.reading.website.api.domain.UserNotesInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 阅读笔记Mapper
 *
 * yx8102   2019/3/17
 */
@Component
public interface UserNotesInfoMapper {

    /**
     * 新增
     * @param notesInfoDO
     * @return
     */
    int insertSelective(UserNotesInfoDO notesInfoDO);

    /**
     * 更新
     * @param notesInfoDO
     * @return
     */
    int updateByPrimaryKeySelective(UserNotesInfoDO notesInfoDO);

    /**
     * 分页查询
     * @param query 查询条件
     * @return
     */
    List<UserNotesInfoDO> pageQuery(UserNotesInfoQuery query);

    /**
     * 根据用户id查询图书id列表
     * @param userId 用户id
     * @param offset 分页起始
     * @param pageSize 页面大小
     * @return
     */
    List<UserNotesInfoDO> selectByUserIdGroupBookId(@Param("userId") Integer userId,
                                       @Param("offset") Integer offset,
                                       @Param("pageSize") Integer pageSize);

    /**
     * 条件查询计数
     * @param query 计数条件
     * @return
     */
    int countPageQuery(UserNotesInfoQuery query);

    /**
     * 图书id查询计数
     * @param userId 用户id
     * @return
     */
    int countSelectByUserIdGroupBookId(@Param("userId") Integer userId);
}