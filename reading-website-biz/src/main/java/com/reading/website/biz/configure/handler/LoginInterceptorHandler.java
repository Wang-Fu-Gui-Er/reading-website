package com.reading.website.biz.configure.handler;

import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.biz.exception.BusinessException;
import com.reading.website.biz.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器
 *
 * @xyang010 2019/4/21
 */
@Component
@Slf4j
public class LoginInterceptorHandler implements HandlerInterceptor {

    /**
     * 访问接口之前执行,在用户调用指定接口之前验证登陆状态
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(token)) {
            log.warn("token为空");
            throw new BusinessException(StatusCodeEnum.TOKEN_EXPIRE.getCode(), StatusCodeEnum.TOKEN_EXPIRE.getMark());
        }
        if (JWTUtil.verify(token) == null) {
            log.warn("token失效");
            throw new BusinessException(StatusCodeEnum.TOKEN_EXPIRE.getCode(), StatusCodeEnum.TOKEN_EXPIRE.getMark());
        }
        return true;
    }
}
