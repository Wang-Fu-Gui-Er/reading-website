package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.AuthorDO;
import com.reading.website.api.service.AuthorService;
import com.reading.website.api.vo.AuthorVO;
import com.reading.website.biz.utils.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者相关接口
 *
 * @xyang010 2019/3/13
 */
@Api(value = "作者相关接口", description = "BookController", tags = {"作者相关接口"})
@RestController
@Slf4j
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation(value="新增或修改作者信息", notes="新增或修改作者信息")
    @PostMapping(value = "/addOrUpdate")
    public BaseResult<Integer> addOrUpdate(@RequestBody AuthorDO authorDO) {
        return authorService.insertOrUpdate(authorDO);
    }

    @ApiOperation(value="查询作者信息", notes="查询作者信息")
    @GetMapping(value = "/getAuthorInfo")
    public BaseResult<AuthorVO> getAuthorInfo(@RequestParam("authorId") Integer authorId) {
        return authorService.selectByAuthorId(authorId);

    }

    @ApiOperation(value="模糊查询作者信息", notes="模糊查询作者信息")
    @GetMapping(value = "/fuzzyQueryAuthorInfo")
    public BaseResult<List<AuthorVO>> fuzzyQueryAuthorInfo(@RequestParam("authorName") String authorName) {
        return authorService.fuzzySelectByAuthorName(authorName);
    }

    @ApiOperation(value="删除作者", notes="删除作者")
    @GetMapping(value = "/del")
    public BaseResult<Integer> fuzzyQueryAuthorInfo(@RequestParam("authorId") Integer authorId) {
        return authorService.delByAuthorId(authorId);
    }
}
