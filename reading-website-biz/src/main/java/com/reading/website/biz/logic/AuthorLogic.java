package com.reading.website.biz.logic;

import com.reading.website.api.domain.AuthorDO;
import com.reading.website.api.vo.AuthorVO;
import com.reading.website.biz.utils.Base64Util;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者逻辑类
 *
 * @xyang010 2019/4/8
 */
@Component
public class AuthorLogic {

    /**
     * 将DO列表转换为VO列表
     * @param authorDOList
     * @return
     */
    public static List<AuthorVO> convertDOs2VOs(List<AuthorDO> authorDOList) {
        if (CollectionUtils.isEmpty(authorDOList)) {
            return null;
        }

        List<AuthorVO> authorVOList = new ArrayList<>(authorDOList.size());
        authorDOList.forEach(authorDO -> {
            authorVOList.add(convertDO2VO(authorDO));
        });

        return authorVOList;
    }

    /**
     * 将DO实体转换为VO实体
     * @param authorDO
     * @return
     */
    public static AuthorVO convertDO2VO(AuthorDO authorDO) {
        if (authorDO == null) {
            return null;
        }

        AuthorVO authorVO = new AuthorVO();
        BeanUtils.copyProperties(authorDO, authorVO);
        authorVO.setAuthorPic(Base64Util.fileToBase64ByLocal(authorDO.getAuthorPic()));
        return authorVO;
    }
}
