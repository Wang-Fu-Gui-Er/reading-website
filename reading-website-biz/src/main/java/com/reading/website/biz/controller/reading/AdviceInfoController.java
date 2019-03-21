package com.reading.website.biz.controller.reading;


import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.AdviceInfoDO;
import com.reading.website.api.domain.AdviceInfoQuery;
import com.reading.website.api.domain.AuthorDO;
import com.reading.website.api.service.AdviceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 反馈接口
 *
 * @xyang010 2019/3/21
 */
@Api(value = "反馈接口", description = "AdviceInfoController", tags = {"反馈接口"})
@RestController
@Slf4j
@RequestMapping("/advice")
public class AdviceInfoController {
    @Autowired
    private AdviceInfoService adviceInfoService;

    @ApiOperation(value="增加反馈信息", notes="增加反馈信息")
    @PostMapping(value = "/add")
    public BaseResult<Integer> add(@RequestBody AdviceInfoDO adviceInfoDO) {
        return adviceInfoService.insert(adviceInfoDO);
    }

    @ApiOperation(value="修改反馈信息", notes="修改反馈信息")
    @PostMapping(value = "/update")
    public BaseResult<Integer> update(@RequestBody AdviceInfoDO adviceInfoDO) {
        return adviceInfoService.update(adviceInfoDO);
    }

    @ApiOperation(value="查询反馈信息", notes="查询反馈信息")
    @PostMapping(value = "/query")
    public BaseResult<List<AdviceInfoDO>> pageQuery(@RequestBody AdviceInfoQuery query) {
        return adviceInfoService.pageQuery(query);
    }


}
