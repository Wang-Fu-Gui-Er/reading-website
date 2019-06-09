package com.reading.website.biz.logic;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.Page;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.*;
import com.reading.website.api.service.BookReviewInfoService;
import com.reading.website.api.service.UserBaseInfoService;
import com.reading.website.api.service.UserGradeInfoService;
import com.reading.website.api.vo.BookReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 评论逻辑类
 *
 * @xyang010 2019/4/13
 */
@Component
@Slf4j
public class ReviewLogic {

    private final BookReviewInfoService reviewInfoService;
    private final UserBaseInfoService userBaseInfoService;
    private final UserGradeInfoService userGradeInfoService;


    @Autowired
    public ReviewLogic(BookReviewInfoService reviewInfoService, UserBaseInfoService userBaseInfoService, UserGradeInfoService userGradeInfoService) {
        this.reviewInfoService = reviewInfoService;
        this.userBaseInfoService = userBaseInfoService;
        this.userGradeInfoService = userGradeInfoService;
    }

    /**
     * 查询评论信息
     * @param query
     * @return
     */
    public BaseResult<List<BookReviewVO>> queryReview(BookReviewInfoQuery query) {
        // 1.查询评论信息
        BaseResult<List<BookReviewInfoDO>> reviewRes = reviewInfoService.pageQuery(query);
        if (!reviewRes.getSuccess()) {
            log.warn("查询图书评论失败, query {}, reviewRes {}", query, reviewRes);
            return BaseResult.errorReturn(StatusCodeEnum.LOGIC_ERROR.getCode(), "查询图书评论信息失败");
        }

        List<BookReviewInfoDO> reviewInfoDOS = reviewRes.getData();
        if (CollectionUtils.isEmpty(reviewInfoDOS)) {
            log.warn("暂无评论信息, query {}", query);
            return BaseResult.rightReturn(new ArrayList<>(), reviewRes.getPage());
        }

        // 2. 获取用户id
        List<Integer> userIds = reviewInfoDOS
                .stream()
                .map(BookReviewInfoDO::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 3. 根据用户id查询用户信息
        UserBaseInfoQuery userQuery = new UserBaseInfoQuery();
        userQuery.setIds(userIds);
        BaseResult<List<UserBaseInfoDO>> userRes = userBaseInfoService.selectSelective(userQuery);
        Map<Integer, UserBaseInfoDO> userMap = new HashMap<>();

        // 4. 获得用户信息Map
        if (userRes.getSuccess()) {
            List<UserBaseInfoDO> userBaseInfoDOS = userRes.getData();
            userMap = userBaseInfoDOS
                    .stream()
                    .collect(Collectors.toMap(UserBaseInfoDO::getId, user -> user, (v1, v2) -> v2));
        }

        // 5. 拼装评论展示实体
        List<BookReviewVO> reviewVOList = new ArrayList<>(reviewInfoDOS.size());
        for (BookReviewInfoDO reviewInfoDO : reviewInfoDOS) {
            BookReviewVO vo = new BookReviewVO();
            BeanUtils.copyProperties(reviewInfoDO, vo);
            vo.setNickName(userMap.get(reviewInfoDO.getUserId()).getNickName());
            vo.setHeadPicPath(userMap.get(reviewInfoDO.getUserId()).getHeadPicPath());

            Integer bookId = query.getBookId();
            if (bookId != null) {
                BaseResult<UserGradeInfoDO> userGradeRes = userGradeInfoService.selectByUserIdAndBookId(reviewInfoDO.getUserId(), bookId);
                if (userGradeRes.getSuccess() && userGradeRes.getData() != null) {
                    vo.setGrade(userGradeRes.getData().getGrade());
                }
            }

            reviewVOList.add(vo);
        }

        return BaseResult.rightReturn(reviewVOList, reviewRes.getPage());
    }
}
