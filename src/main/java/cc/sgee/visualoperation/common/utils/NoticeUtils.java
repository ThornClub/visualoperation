package cc.sgee.visualoperation.common.utils;

import cc.sgee.visualoperation.common.pojo.MonitorEmailReceive;
import cc.sgee.visualoperation.common.pojo.MonitorEmailSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

/**
 * @author: Thorn
 * @Date: 2021/3/7 00:07
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Component
@RefreshScope
public class NoticeUtils {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private MonitorEmailSend send;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${monitor.remind.wx.key}")
    private String wx_key;

    /**
     * 通过email发送报警信息
     * @param receive
     * @param text
     */
    public void SendEmail(String receive, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("服务器异常");
        message.setFrom(send.getSend_email());
        message.setTo(receive);
        message.setSentDate(new Date());
        message.setText(text);
        javaMailSender.send(message);
    }

    /**
     * 通过wx发送报警信息
     * @param text
     */
    public void SendWx(String text){
        if (wx_key != null) {
            restTemplate.getForObject("https://sc.ftqq.com/" + wx_key + ".send?text=服务器异常&desp=" + text, String.class);
        }
    }
}
