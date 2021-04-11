package cc.sgee.visualoperation.common.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Repository;

/**
 * @author: Thorn
 * @Date: 2020/11/27 14:21
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Getter
@Setter
@RefreshScope
@Repository
public class User {
    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;
}
