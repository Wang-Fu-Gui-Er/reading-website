package com.reading.website.biz.mapper;

import com.reading.website.api.domain.SmallCategoryDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图书分类，小类Mapper
 *
 * @yx8102 2019/1/16
 */
@Component
public interface SmallCategoryMapper {

    /**
     * 查询首页展示的二级分类
     * @return
     */
    List<SmallCategoryDO> listHomeSmallCategory();

    /**
     * 根据大类id批量查询小类
     * @param bigCateIds
     * @return
     */
    List<SmallCategoryDO> listByBigCateIds(@Param("bigCateIds") List<Integer> bigCateIds);

    /**
     * 新增小类
     * @param record
     * @return
     */
    int insertSelective(SmallCategoryDO record);

    /**
     * 根据小类id查询
     * @param id
     * @return
     */
    SmallCategoryDO selectByPrimaryKey(Integer id);



    int deleteByPrimaryKey(Integer id);

    int insert(SmallCategoryDO record);

    int updateByPrimaryKeySelective(SmallCategoryDO record);

    int updateByPrimaryKey(SmallCategoryDO record);

}