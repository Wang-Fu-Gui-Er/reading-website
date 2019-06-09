package com.reading.website.biz.logic;

import com.alibaba.fastjson.JSON;
import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import com.reading.website.api.domain.BookGradeInfoDO;
import com.reading.website.api.domain.UserGradeInfoDO;
import com.reading.website.api.service.BookGradeInfoService;
import com.reading.website.api.service.UserGradeInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 评分逻辑类
 *
 * @xyang010 2019/6/9
 */
@Component
@Slf4j
public class GradeLogic {

    private final BookGradeInfoService bookGradeInfoService;
    private final UserGradeInfoService userGradeInfoService;

    @Autowired
    public GradeLogic(BookGradeInfoService bookGradeInfoService, UserGradeInfoService userGradeInfoService) {
        this.bookGradeInfoService = bookGradeInfoService;
        this.userGradeInfoService = userGradeInfoService;
    }

    public BaseResult<Integer> updateGrade(UserGradeInfoDO userGradeInfoDO) {

        //1. 保存用户评分
        BaseResult<Integer> insertUserGradeRes = userGradeInfoService.insertOrUpdate(userGradeInfoDO);
        if (!insertUserGradeRes.getSuccess()) {
            return insertUserGradeRes;
        }

        //2. 查询图书评分
        BaseResult<BookGradeInfoDO> bookGradeRes = bookGradeInfoService.selectByBookId(userGradeInfoDO.getBookId());
        if (!bookGradeRes.getSuccess()) {
            return BaseResult.errorReturn(StatusCodeEnum.LOGIC_ERROR.getCode(), "查询图书评分失败");
        }

        //3. 更新图书评分
        BookGradeInfoDO bookGradeInfoDO = bookGradeRes.getData();
        Integer grade = userGradeInfoDO.getGrade();
        if (bookGradeInfoDO == null) {
            bookGradeInfoDO = new BookGradeInfoDO();
            bookGradeInfoDO.setBookId(userGradeInfoDO.getBookId());
            bookGradeInfoDO.setOneNum(0);
            bookGradeInfoDO.setTwoNum(0);
            bookGradeInfoDO.setThreeNum(0);
            bookGradeInfoDO.setFourNum(0);
            bookGradeInfoDO.setFiveNum(0);
        }

        if (grade == 1) {
            bookGradeInfoDO.setOneNum(bookGradeInfoDO.getOneNum() + 1);
        } else if (grade == 2) {
            bookGradeInfoDO.setTwoNum(bookGradeInfoDO.getTwoNum() + 1);
        } else if (grade == 3) {
            bookGradeInfoDO.setThreeNum(bookGradeInfoDO.getThreeNum() + 1);
        } else if (grade == 4) {
            bookGradeInfoDO.setFourNum(bookGradeInfoDO.getFourNum() + 1);
        } else if (grade == 5) {
            bookGradeInfoDO.setFiveNum(bookGradeInfoDO.getFiveNum() + 1);
        }

        return bookGradeInfoService.insertOrUpdate(bookGradeInfoDO);
    }
}
