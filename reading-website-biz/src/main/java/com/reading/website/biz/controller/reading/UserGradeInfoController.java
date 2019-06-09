package com.reading.website.biz.controller.reading;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.domain.UserGradeInfoDO;
import com.reading.website.api.service.UserGradeInfoService;
import com.reading.website.biz.logic.GradeLogic;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户评分接口
 *
 * @xyang010 2019/6/8
 */
@Api(value = "用户评分相关接口", description = "UserGradeInfoController", tags = {"用户评分相关接口"})
@RestController
@Slf4j
@RequestMapping("/user/gradeInfo")
public class UserGradeInfoController {

    private final UserGradeInfoService userGradeInfoService;

    private final GradeLogic gradeLogic;

    @Autowired
    public UserGradeInfoController(UserGradeInfoService userGradeInfoService, GradeLogic gradeLogic) {
        this.userGradeInfoService = userGradeInfoService;
        this.gradeLogic = gradeLogic;
    }

    @ApiOperation(value="保存评分", notes="保存评分")
    @PostMapping(value = "/save")
    public BaseResult<Integer> save(@RequestBody UserGradeInfoDO userGradeInfoDO) {

        return gradeLogic.updateGrade(userGradeInfoDO);
    }

    @ApiOperation(value="查询评分", notes="查询评分")
    @GetMapping(value = "/queryByUserIdAndBookId")
    public BaseResult<UserGradeInfoDO> save(@RequestParam("bookId") Integer bookId, HttpServletRequest request) {
        if (bookId == null) {
            log.warn("UserGradeInfoController save param bookId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param bookId is null");
        }

        LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
        if (loginInfoDTO == null) {
            log.warn("UserGradeInfoController save param userId is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param userId is null");
        }

        return userGradeInfoService.selectByUserIdAndBookId(loginInfoDTO.getUserId(), bookId);
    }


}
