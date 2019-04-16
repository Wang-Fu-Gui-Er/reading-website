package com.reading.website.biz.service.impl;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BigCategoryDO;
import com.reading.website.api.domain.CategoryDTO;
import com.reading.website.api.domain.SmallCategoryDO;
import com.reading.website.api.service.CategoryService;
import com.reading.website.api.vo.CategoryVO;
import com.reading.website.biz.mapper.BigCategoryMapper;
import com.reading.website.biz.mapper.SmallCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 图书分类服务实现类
 *
 * @xyang010 2019/1/16
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private BigCategoryMapper bigCategoryMapper;

    @Autowired
    private SmallCategoryMapper smallCategoryMapper;

    /**
     * 查询首页展示的二级分类列表
     * @return
     */
    @Override
    public BaseResult<List<SmallCategoryDO>> listHomeSmallCategory() {
        try {
            return BaseResult.rightReturn(smallCategoryMapper.listHomeSmallCategory());
        } catch (Exception e) {
            log.error("CategoryService listHomeSmallCategory error {}", e);
            return BaseResult.errorReturn(null, StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 查询所有分类
     * @return
     */
    @Override
    public BaseResult<List<CategoryVO>> listAllCategory() {

        try {
            List<CategoryVO> categoryVOList = new ArrayList<>();

            //1. 查询所有大类
            List<BigCategoryDO> bigCategoryList = bigCategoryMapper.listAll();
            if (!CollectionUtils.isEmpty(bigCategoryList)) {
                List<Integer> bigCateIds = bigCategoryList
                        .stream()
                        .map(BigCategoryDO::getId)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());
                //2. 根据大类查询小类
                List<SmallCategoryDO> smallCateList = smallCategoryMapper.listByBigCateIds(bigCateIds);

                //3. 拼装结果集
                if (!CollectionUtils.isEmpty(smallCateList)) {
                    bigCategoryList.forEach(bigCategoryDO -> {
                        List<SmallCategoryDO> smallCategoryDOList = new ArrayList<>();
                        smallCateList.forEach(smallCategoryDO -> {
                            if (bigCategoryDO.getId().equals(smallCategoryDO.getBigCateId())) {
                                smallCategoryDOList.add(smallCategoryDO);
                            }
                        });
                        CategoryVO categoryVO = new CategoryVO();
                        categoryVO.setBigCategoryDO(bigCategoryDO);
                        categoryVO.setSmallCategoryList(smallCategoryDOList);
                        categoryVOList.add(categoryVO);
                    });
                }
            }
            return BaseResult.rightReturn(categoryVOList);
        } catch (Exception e) {
            log.error("CategoryService listAllCategory error {}", e);
            return BaseResult.errorReturn(StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据大类id查询小类
     * @param bigCateId
     * @return
     */
    @Override
    public BaseResult<List<SmallCategoryDO>> listSmallCateByBigCateId(Integer bigCateId) {
        if (bigCateId == null) {
            log.warn("CategoryService listSmallCateByBigCateId param bigCateId is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param bigCateId is null");
        }
        try {
            List<Integer> bigCateIds = new ArrayList<>();
            bigCateIds.add(bigCateId);
            return BaseResult.rightReturn(smallCategoryMapper.listByBigCateIds(bigCateIds));
        } catch (Exception e) {
            log.error("CategoryService listSmallCateByBigCateIds error {}", e);
            return BaseResult.errorReturn(null, StatusCodeEnum.MAPPER_ERROR.getCode(), "mapper error");
        }
    }

    /**
     * 根据小类id列表查全部分类信息
     * @return
     */
    @Override
    public BaseResult<CategoryDTO> getCategoryBySmallCateId(Integer smallCateId) {
        if (smallCateId == null) {
            log.warn("CategoryService listCategoryBySmallCateId param smallCateId is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param smallCateId is null");
        }

        SmallCategoryDO smallCategoryDO = smallCategoryMapper.selectByPrimaryKey(smallCateId);
        if (smallCategoryDO == null) {
            log.warn("二级分类不存在, smallCateId {}", smallCateId);
            return BaseResult.rightReturn(null);
        }

        BigCategoryDO bigCategoryDO = bigCategoryMapper.selectByPrimaryKey(smallCategoryDO.getBigCateId());
        if (bigCategoryDO == null) {
            log.warn("一级分类不存在, smallCateId {}, bigCateId", smallCateId, smallCategoryDO.getBigCateId());
            return BaseResult.rightReturn(null);
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setSmallCateId(smallCategoryDO.getId());
        categoryDTO.setSmallCateName(smallCategoryDO.getCateName());
        categoryDTO.setBigCateId(bigCategoryDO.getId());
        categoryDTO.setBigCateName(bigCategoryDO.getCateName());

        return BaseResult.rightReturn(categoryDTO);

    }
}
