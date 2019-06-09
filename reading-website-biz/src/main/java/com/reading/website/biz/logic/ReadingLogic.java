package com.reading.website.biz.logic;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.UserReadingInfoDO;
import com.reading.website.api.domain.UserReadingInfoQuery;
import com.reading.website.api.service.UserReadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * 阅读逻辑类
 *
 * @xyang010 2019/6/8
 */

@Component
@Slf4j
public class ReadingLogic {

    private final UserReadingService readingService;

    @Autowired
    public ReadingLogic(UserReadingService readingService) {
        this.readingService = readingService;
    }

    public void saveReadingInfo (Integer userId, Integer bookId, Integer chapterId) {

        // 查询是否存在阅读记录
        UserReadingInfoQuery query = new UserReadingInfoQuery();
        query.setBookId(bookId);
        query.setUserId(userId);
        BaseResult<List<UserReadingInfoDO>> queryReadingRes = readingService.pageQuery(query);
        if (queryReadingRes.getSuccess()) {
            List<UserReadingInfoDO> readingInfoDOList = queryReadingRes.getData();
            // 无阅读记录，增加阅读记录;有阅读记录，更新阅读记录
            Integer readindId = null;
            if (!CollectionUtils.isEmpty(readingInfoDOList)) {
                UserReadingInfoDO readingInfoDO = readingInfoDOList.get(0);
                readindId = readingInfoDO.getId();
            }
            UserReadingInfoDO readingInfoDO = new UserReadingInfoDO();
            readingInfoDO.setId(readindId);
            readingInfoDO.setBookId(bookId);
            readingInfoDO.setChapId(chapterId);
            readingInfoDO.setUserId(userId);
            BaseResult<Integer> saveReadingInfoRes = readingService.insertOrUpdate(readingInfoDO);
            if (!saveReadingInfoRes.getSuccess()) {
                log.warn("记录用户阅读信息失败 saveReadingInfoRes {}", saveReadingInfoRes);
            }
        }
    }
}
