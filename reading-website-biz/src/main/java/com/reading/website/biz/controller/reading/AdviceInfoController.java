package com.reading.website.biz.controller.reading;


import com.reading.website.api.base.BaseResult;
import com.reading.website.api.domain.AdviceInfoDO;
import com.reading.website.api.domain.AdviceInfoQuery;
import com.reading.website.api.domain.AuthorDO;
import com.reading.website.api.domain.LoginInfoDTO;
import com.reading.website.api.enums.AdviceHandleStatusEnum;
import com.reading.website.api.enums.AdviceTypeEnum;
import com.reading.website.api.service.AdviceInfoService;
import com.reading.website.biz.common.mail.MailService;
import com.reading.website.biz.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private final AdviceInfoService adviceInfoService;

    private final MailService mailService;

    @Autowired
    public AdviceInfoController(AdviceInfoService adviceInfoService, MailService mailService) {
        this.adviceInfoService = adviceInfoService;
        this.mailService = mailService;
    }

    @ApiOperation(value="增加反馈信息", notes="增加反馈信息")
    @PostMapping(value = "/add")
    public BaseResult<Integer> add(@RequestBody AdviceInfoDO adviceInfoDO, HttpServletRequest request) {
        LoginInfoDTO loginInfoDTO = UserUtil.getUserLoginInfo(request);
        if (loginInfoDTO != null) {
            adviceInfoDO.setUserId(loginInfoDTO.getUserId());
        }

        return adviceInfoService.insert(adviceInfoDO);
    }

    @ApiOperation(value="修改反馈信息", notes="修改反馈信息")
    @PostMapping(value = "/update")
    public BaseResult<Integer> update(@RequestBody AdviceInfoDO adviceInfoDO) {
        BaseResult<Integer> updateRes = adviceInfoService.update(adviceInfoDO);
        if (updateRes.getSuccess()) {
            Integer adviceId = adviceInfoDO.getId();
            AdviceInfoQuery query = new AdviceInfoQuery();
            query.setId(adviceId);
            BaseResult<List<AdviceInfoDO>> queryRes = adviceInfoService.pageQuery(query);
            if (queryRes.getSuccess()) {
                AdviceInfoDO dbAdvice = queryRes.getData().get(0);
                String subject = "【图书阅读网站】反馈处理通知";
                StringBuilder content = new StringBuilder();
                content.append("您好，感谢使用图书阅读网站，感谢您的反馈！以下是处理进度。");
                content.append("<table border = '1' cellSpacing = '0'>");

                content.append("<tr><td>反馈类型</td><td>");
                AdviceTypeEnum adviceTypeEnum = AdviceTypeEnum.getAdviceType(dbAdvice.getType());
                content.append(adviceTypeEnum == null ? "未知" : adviceTypeEnum.getDesc());
                content.append("</td></tr>");

                content.append("<tr><td>反馈标题</td><td>");
                content.append(dbAdvice.getTitle());
                content.append("</td></tr>");

                content.append("<tr><td>反馈内容</td><td>");
                content.append(dbAdvice.getDetail());
                content.append("</td></tr>");

                content.append("<tr><td>处理进度</td><td>");
                AdviceHandleStatusEnum adviceHandleStatusEnum = AdviceHandleStatusEnum.getAdviceStatus(dbAdvice.getStatus());
                content.append(adviceHandleStatusEnum == null ? "未知" : adviceHandleStatusEnum.getDesc());
                content.append("</td></tr></table>");

                BaseResult mailRes = mailService.send(dbAdvice.getEmail(), subject, content.toString());
                if (!mailRes.getSuccess()) {
                    log.warn("发送邮件失败");
                }
            }
        }

        return updateRes;
    }

    @ApiOperation(value="查询反馈信息", notes="查询反馈信息")
    @PostMapping(value = "/query")
    public BaseResult<List<AdviceInfoDO>> pageQuery(@RequestBody AdviceInfoQuery query) {
        return adviceInfoService.pageQuery(query);
    }


}
