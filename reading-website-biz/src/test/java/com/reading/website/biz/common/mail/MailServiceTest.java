package com.reading.website.biz.common.mail;

import com.reading.website.api.base.BaseResult;
import com.reading.website.biz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱服务测试类
 *
 * @xyang010 2019/2/26
 */
public class MailServiceTest extends BaseTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSend() {
        List<String> receivers = new ArrayList<>();
        receivers.add("1406027395@qq.com");
        receivers.add("XYANG010@MAIL.NFSQ.COM.CN");
        String subject = "测试邮件";
        String content = "这是一封测试邮件";
        BaseResult result = mailService.send(receivers, subject, content);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getSuccess());
    }
}
