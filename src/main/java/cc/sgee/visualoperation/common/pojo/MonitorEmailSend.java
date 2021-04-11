package cc.sgee.visualoperation.common.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

/**
 * @author: Thorn
 * @Date: 2021/3/5 01:11
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Getter
@Setter
@Repository
@RefreshScope
public class MonitorEmailSend {
    @Value("${spring.mail.username}")
    private String send_email;

    @Value("${spring.mail.password}")
    private String smtp_password;

    @Value("${spring.mail.host}")
    private String smtp_server;

    @Value("${spring.mail.port}")
    private int smtp_port;
}
