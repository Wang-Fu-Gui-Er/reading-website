package com.reading.website.biz.utils;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.reading.website.api.domain.LoginInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 用户工具类
 *
 * @xyang010 2019/4/21
 */
@Slf4j
public class UserUtil {

    /**
     * 获取用户登陆信息
     * @param request
     * @return
     */
    public static LoginInfoDTO getUserLoginInfo(HttpServletRequest request) {
        String token = request.getHeader("Access-Token");
        if (StringUtils.isEmpty(token)) {
            log.warn("token为空");
            return null;
        }

        DecodedJWT decodedJWT = JWTUtil.verify(token);
        if (decodedJWT != null) {
            log.warn("token失效");
            return null;
        }

        Map<String, Claim> claimMap = decodedJWT.getClaims();
        Claim userIdClaim = claimMap.get("userId");
        Claim emailClaim = claimMap.get("email");
        Integer userId = userIdClaim.asInt();
        String email = emailClaim.asString();

        return new LoginInfoDTO(userId, email);
    }
}
