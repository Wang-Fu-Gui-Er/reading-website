package com.reading.website.biz.mapper;

import com.reading.website.api.domain.UserGradeInfoDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserGradeInfoMapper {

    /**
     * 新增或更新
     * @param userGradeInfoDO
     * @return
     */
    int insertOrUpdate(UserGradeInfoDO userGradeInfoDO);

    /**
     * 根据用户id和图书id查询
     * @param userId
     * @return
     */
    UserGradeInfoDO selectByUserIdAndBookId(@Param("userId") Integer userId, @Param("bookId") Integer bookId);

}