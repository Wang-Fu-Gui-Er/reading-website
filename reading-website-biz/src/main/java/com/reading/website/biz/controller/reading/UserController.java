package com.reading.website.biz.controller.reading;

import com.alibaba.fastjson.JSON;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.constants.UserStatusConstant;
import com.reading.website.api.domain.UserBaseInfoDO;
import com.reading.website.api.domain.UserBaseInfoQuery;
import com.reading.website.api.service.UserBaseInfoService;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.biz.common.mail.MailService;
import com.reading.website.biz.utils.EhcacheUtil;
import com.reading.website.biz.utils.EncryptUtil;
import com.reading.website.biz.utils.JWTUtil;
import com.reading.website.biz.utils.VerificationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private MailService mailService;

    @Autowired
    private EhcacheUtil cache;

    /**
     * 用户注册
     * @param userBaseInfoDO email,password
     * @return
     */
    @ApiOperation(value="用户注册操作", notes="注册")
    @PostMapping(value = "/register")
    public BaseResult<String> register(@RequestBody UserBaseInfoDO userBaseInfoDO) {
        if (!checkParam(userBaseInfoDO)) {
            log.warn("user register param user error");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param user error");
        }

        //设置用户状态为 未激活
        userBaseInfoDO.setStatus(UserStatusConstant.INACTIVE);
        // 前端使用MD5加密，后端使用SHA1加密
        userBaseInfoDO.setPassword(EncryptUtil.getInstance().SHA1(userBaseInfoDO.getPassword()));
        BaseResult<Integer> serviceRes = userService.insertSelective(userBaseInfoDO);
        if (serviceRes != null && serviceRes.getSuccess()) {
            return BaseResult.rightReturn(userBaseInfoDO.getEmail());
        }

        return BaseResult.errorReturn(StatusCodeEnum.SERVICE_ERROR.getCode(), "inner service error");
    }

    /**
     * 发送邮件验证码接口
     * @param email 用户邮箱地址
     * @param type 邮件校验码类型
     * @return
     */
    @ApiOperation(value="发送邮件验证码接口", notes="发送邮件验证码接口")
    @GetMapping(value = "/verifyCode/send")
    public BaseResult sendVerifyCode(@Param("email") String email, @Param("type") Integer type) {
        if (StringUtils.isEmpty(email) || type == null) {
            log.warn("user sendVerificationCode param error");
            return BaseResult.errorReturn(false, StatusCodeEnum.PARAM_ERROR.getCode(), "userId null");

        }

        // 生成验证码
        String verificationCode = VerificationCodeUtil.createVerificationCode();

        // 放入缓存中
        cache.put(EhcacheUtil.VERIFY_CODE_CACHE, email, verificationCode);

        // 拼装邮件信息
        String subject = "";
        String content = "";
        // 根据邮件类型，设置文案
        switch (type) {
            case 0: {   // 注册激活
                subject = "【图书阅读网站】用户注册激活邮件";
                content = "用户您好，欢迎使用图书阅读网站，验证码为:" + verificationCode;
                break;
            }
        }
        BaseResult result = mailService.send(email, subject, content);
        if (!result.getSuccess()) {
            log.warn("发送邮件验证码失败");
        }
        return result;
    }


    /**
     * 邮件验证接口
     * @param email 邮箱地址
     * @return
     */
    @ApiOperation(value="用户操作验证接口", notes="用户操作验证接口")
    @GetMapping(value = "/verifyCode/check")
    public BaseResult<Boolean> checkVerifyCode(@Param("email") String email, @Param("verifyCode") String verifyCode) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(verifyCode)) {
            log.warn("user checkVerifyCode param error");
            return BaseResult.errorReturn(false, StatusCodeEnum.PARAM_ERROR.getCode(), "checkVerifyCode param null");

        }
        Object verifyCodeCache = cache.get(EhcacheUtil.VERIFY_CODE_CACHE, email);
        if (verifyCode.equals(String.valueOf(verifyCodeCache))) {
            // 更改用户状态为正常
            UserBaseInfoDO userBaseInfoDO = new UserBaseInfoDO();
            userBaseInfoDO.setStatus(UserStatusConstant.NORMAL);
            userBaseInfoDO.setEmail(email);
            BaseResult<Integer> updateRes = userService.updateByEmailSelective(userBaseInfoDO);
            if (updateRes.getSuccess()) {
                log.info("激活用户状态成功");
                return BaseResult.rightReturn(true);
            } else {
                log.warn("激活用户状态失败");
                return BaseResult.rightReturn(false);
            }
        }
        return BaseResult.rightReturn(false);
    }

    /**
     * 参数校验
     * @param userBaseInfoDO
     * @return
     */
    private boolean checkParam(UserBaseInfoDO userBaseInfoDO) {
        return  !(userBaseInfoDO == null
                || StringUtils.isEmpty(userBaseInfoDO.getEmail())
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
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param user is null");
        }

        if (StringUtils.isEmpty(userBaseInfoDO.getEmail())) {
            log.warn("user login param email is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param email is null");

        }

        if (StringUtils.isEmpty(userBaseInfoDO.getPassword())) {
            log.warn("user login param password is null");
            return BaseResult.errorReturn(null, StatusCodeEnum.PARAM_ERROR.getCode(), "param password is null");

        }

        // 验证用户是否存在 邮箱地址
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        query.setEmail(userBaseInfoDO.getEmail());
        BaseResult<List<UserBaseInfoDO>> dbUserRes = userService.selectSelective(query);
        if (!dbUserRes.getSuccess()) {
            log.warn("user login query user by email error, email is {}", query.getNickName());
            return BaseResult.errorReturn(null, StatusCodeEnum.INNER_SERVICE_ERROR.getCode(), "query user by email error");
        }

        if (CollectionUtils.isEmpty(dbUserRes.getData())) {
            log.warn("user login query user by email, email {} not exist");
            return BaseResult.errorReturn(null, StatusCodeEnum.NOT_FOUND.getCode(), "user not found, please register!");
        }

        //验证密码是否正确
        UserBaseInfoDO dbUser = dbUserRes.getData().get(0);
        String loginPassWord = EncryptUtil.getInstance().SHA1(userBaseInfoDO.getPassword());
        if (!loginPassWord.equals(dbUser.getPassword())) {
            log.warn("user login refused, password is error, user is {}", JSON.toJSON(userBaseInfoDO));
            return BaseResult.errorReturn(null, StatusCodeEnum.PASSWORD_ERROR.getCode(), "password error");

        }

        // 邮箱，密码验证成功，
        String token = JWTUtil.sign(dbUser.getEmail(), dbUser.getId());
        if (StringUtils.isEmpty(token)) {
            log.warn("user login create token error, user is {}", userBaseInfoDO);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("userBaseInfo", dbUser);
        res.put("token", token);
        return BaseResult.rightReturn(res);
    }

}
