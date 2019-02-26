package com.reading.website.biz.common.mail;

import com.reading.website.api.base.BaseResult;
import com.reading.website.api.base.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮箱服务
 *
 * @xyang010 2019/2/26
 */
@Service
@Slf4j
public class MailService {
    // 邮箱服务器登陆账号
    private static final String ACCOUNT = "1912246570@qq.com";
    // 邮箱服务器登陆密码
    private static final String PASSWORD = "rbukzwwkncbbdcij";
    // 邮箱服务器地址
    private static final String HOST = "smtp.qq.com";

    /**
     * 发送邮件
     * @param receiver 收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return
     */
    public BaseResult send(String receiver, String subject, String content) {
        if (StringUtils.isEmpty(receiver)) {
            log.warn("MailService send receiver is null");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "receiver is null");
        }
        List<String> receivers = new ArrayList<>();
        receivers.add(receiver);
        return send(receivers, subject, content);
    }

    /**
     * 发送邮件
     * @param receivers 收件人地址列表
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return
     */
    public BaseResult send(List<String> receivers, String subject, String content) {
        if (CollectionUtils.isEmpty(receivers) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(content)) {
            log.warn("MailService send param error");
            return BaseResult.errorReturn(StatusCodeEnum.PARAM_ERROR.getCode(), "param error");
        }

        try {
            //1. 根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(getProp());

            //2. 设置为debug模式, 可以查看详细的发送 log
            session.setDebug(true);

            //3. 生成邮件信息
            MimeMessage message = createMessage(session, ACCOUNT, receivers, subject, content);

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            transport.connect(ACCOUNT, PASSWORD);

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
            return BaseResult.rightReturn(null);
        } catch (Exception e) {
            log.error("发送邮件失败，error {}", e);
            return BaseResult.errorReturn(StatusCodeEnum.EMAIL_ERROR.getCode(), "邮件发送失败");
        }
    }


    /**
     * 邮件信息
     * @param session 邮箱服务会话
     * @param account 发件人地址
     * @param receivers 收件人地址列表
     * @param subject 邮件主题
     * @param content 邮件正文
     * @return message 邮件信息
     */
    private MimeMessage createMessage(Session session, String account, List<String> receivers, String subject, String content) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(account, "图书阅读网站", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        InternetAddress[] addresses = new InternetAddress[receivers.size()];
        for (int i = 0; i < receivers.size(); i++) {
            addresses[i] = new InternetAddress(receivers.get(i));
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);

        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 创建参数配置, 用于连接邮件服务器的参数配置
     * @return
     */
    private Properties getProp() {
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", HOST);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        return props;
    }
}
