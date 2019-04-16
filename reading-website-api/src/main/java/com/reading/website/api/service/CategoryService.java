package com.reading.website.api.service;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.CategoryDTO;
import com.reading.website.api.domain.SmallCategoryDO;
import com.reading.website.api.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图书分类服务类
 *
 * @xyang010 2019/1/16
 */
@Service
public interface CategoryService {

    /**
     * 查询首页展示的二级分类列表
     * @return
     */
    BaseResult<List<SmallCategoryDO>> listHomeSmallCategory();

    /**
     * 查询所有分类
     * @return
     */
    BaseResult<List<CategoryVO>> listAllCategory();

    /**
     * 根据大类id查询小类
     * @param bigCateId
     * @return
     */
    BaseResult<List<SmallCategoryDO>> listSmallCateByBigCateId(Integer bigCateId);

    /**
     * 根据小类id查全部分类信息
     * @return
     */
    BaseResult<CategoryDTO> getCategoryBySmallCateId(Integer smallCateId);


}
