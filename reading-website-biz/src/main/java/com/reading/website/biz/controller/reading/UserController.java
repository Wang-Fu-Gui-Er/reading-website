package com.reading.website.biz.controller.reading;

import com.alibaba.fastjson.JSON;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoQuery;
import com.reading.website.api.service.UserBaseInfoService;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.biz.utils.EncryptUtil;
import com.reading.website.biz.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户注册、登录接口
 *
 * @xyang010 2019/1/9
 */
@Api(value = "用户相关接口", description = "UserController", tags = {"用户相关接口"})
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBaseInfoService userService;

    /**
     * 用户注册
     * @param userBaseInfoDO
     * @return
     */
    @ApiOperation(value="用户注册操作", notes="注册")
    @PostMapping(value = "/register")
    public BaseResult<Boolean> register(@RequestBody UserBaseInfoDO userBaseInfoDO) {
        if (!checkParam(userBaseInfoDO)) {
            log.warn("user register param user error");
            return BaseResult.errorReturn(false, StatusCodeEnum.PARAM_ERROR, "param user error");
        }

        // 前端使用MD5加密，后端使用SHA1加密
        userBaseInfoDO.setPassword(EncryptUtil.getInstance().SHA1(userBaseInfoDO.getPassword()));
        BaseResult<Integer> serviceRes = userService.insertSelective(userBaseInfoDO);
        if (serviceRes != null && serviceRes.getSuccess()) {
            return BaseResult.rightReturn(true);
        }

        return BaseResult.errorReturn(false, StatusCodeEnum.SERVICE_ERROR, "inner service error");
    }

    /**
     * 参数校验
     * @param userBaseInfoDO
     * @return
     */
    private boolean checkParam(UserBaseInfoDO userBaseInfoDO) {
        return  !(userBaseInfoDO == null
                || StringUtils.isEmpty(userBaseInfoDO.getNickName())
                ||StringUtils.isEmpty(userBaseInfoDO.getPassword()));
    }

    /**
     * 用户登录
     * @param userBaseInfoDO
     * @return
     */
    @ApiOperation(value="用户登录操作", notes="登录")
    @PostMapping("/login")
    public BaseResult<Map<String, Object>> login(@RequestBody UserBaseInfoDO userBaseInfoDO) {
        // 前后端两次验证，保障程序健壮性
        if (userBaseInfoDO == null) {
            log.warn("user login param user is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR, "param user is null");
        }

        if (StringUtils.isEmpty(userBaseInfoDO.getNickName())) {
            log.warn("user login param nickName is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR, "param nickName is null");

        }

        if (StringUtils.isEmpty(userBaseInfoDO.getPassword())) {
            log.warn("user login param password is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR, "param password is null");

        }

        // 验证用户是否存在 用户名
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        query.setNickName(userBaseInfoDO.getNickName());
        BaseResult<List<UserBaseInfoDO>> dbUserRes = userService.selectSelective(query);
        if (!dbUserRes.getSuccess()) {
            log.warn("user login query user by nickName error, nickName is {}", query.getNickName());
            return BaseResult.errorReturn(null, StatusCodeEnum.INNER_SERVICE_ERROR, "query user by nickName error");
        }

        if (CollectionUtils.isEmpty(dbUserRes.getData())) {
            log.warn("user login query user by nickName, nickName {} not exist");
            return BaseResult.errorReturn(null, StatusCodeEnum.NOT_FOUND, "user not found, please register!");
        }

        //验证密码是否正确
        UserBaseInfoDO dbUser = dbUserRes.getData().get(0);
        String loginPassWord = EncryptUtil.getInstance().SHA1(userBaseInfoDO.getPassword());
        if (!loginPassWord.equals(dbUser.getPassword())) {
            log.warn("user login refused, password is error, user is {}", JSON.toJSON(userBaseInfoDO));
            return BaseResult.errorReturn(null, StatusCodeEnum.PASSWORD_ERROR, "password error");

        }
        // 用户名，密码验证成功，
        String token = JWTUtil.sign(dbUser.getNickName(), dbUser.getId());
        if (StringUtils.isEmpty(token)) {
            log.warn("user login refused, password is error, user is {}", JSON.toJSON(userBaseInfoDO));
        }
        Map<String, Object> res = new HashMap<>();
        res.put("userBaseInfo", dbUser);
        res.put("token", token);
        return BaseResult.rightReturn(res);
    }

}
