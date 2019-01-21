package com.reading.website.biz.mapper;

import com.reading.website.api.domain.BigCategoryDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 图书分类，大类Mapper
 *
 * @yx8102 2019/1/16
 */
@Component
public interface BigCategoryMapper {

    /**
     * 查询所有大类
     * @return
     */
    List<BigCategoryDO> listAll();

    /**
     * 增加大类
     * @param record
     * @return
     */
    int insertSelective(BigCategoryDO record);


    int deleteByPrimaryKey(Integer id);

    int insert(BigCategoryDO record);

    BigCategoryDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BigCategoryDO record);

    int updateByPrimaryKey(BigCategoryDO record);

}