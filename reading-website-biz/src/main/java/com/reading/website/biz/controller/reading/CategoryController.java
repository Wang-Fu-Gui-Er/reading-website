package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BigCategoryDO;
import com.reading.website.api.domain.SmallCategoryDO;
import com.reading.website.api.service.CategoryService;
import com.reading.website.api.vo.CategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 图书分类接口
 *
 * @xyang010 2019/1/21
 */
@Api(value = "分类相关接口", description = "CategoryController", tags = {"分类相关接口"})
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value="获取首页二级分类", notes="首页二级分类")
    @GetMapping(value = "/homeSmallCategory")
    public BaseResult<List<SmallCategoryDO>> listHomeSmallCategory() {
        BaseResult<List<SmallCategoryDO>> result = categoryService.listHomeSmallCategory();
        if (!result.getSuccess()) {
            result.setCode(StatusCodeEnum.INNER_SERVICE_ERROR.getCode());
            result.setMessage(StatusCodeEnum.INNER_SERVICE_ERROR.getMark());
        }
        return result;
    }

    @ApiOperation(value="获取所有分类", notes="获取所有分类")
    @GetMapping(value = "/getAllCategory")
    public BaseResult<List<CategoryVO>> listAllCategory() {
        BaseResult<List<CategoryVO>> result = categoryService.listAllCategory();
        if (!result.getSuccess()) {
            result.setCode(StatusCodeEnum.INNER_SERVICE_ERROR.getCode());
            result.setMessage(StatusCodeEnum.INNER_SERVICE_ERROR.getMark());
        }
        return result;
    }


    @ApiOperation(value="查询有声文学二级分类列表", notes="有声文学二级分类列表")
    @GetMapping(value = "/audioBookSmallCate")
    public BaseResult<List<SmallCategoryDO>> listAudioBookSmallCate(@RequestParam("bigCateId") Long bigCateId) {
        BaseResult<List<SmallCategoryDO>> result = categoryService.listSmallCateByBigCateId(bigCateId);
        if (!result.getSuccess()) {
            result.setCode(StatusCodeEnum.INNER_SERVICE_ERROR.getCode());
            result.setMessage(StatusCodeEnum.INNER_SERVICE_ERROR.getMark());
        }
        return result;
    }

}
