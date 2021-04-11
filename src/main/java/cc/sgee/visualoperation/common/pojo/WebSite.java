package cc.sgee.visualoperation.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author: Thorn
 * @Date: 2021/2/28 14:56
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Getter
@Setter
@RefreshScope
@Component
@ApiModel(description = "面板信息")
public class WebSite {
    @ApiModelProperty(value = "网站名",required = true)
    @Value("${website.name}")
    private String name;

    @ApiModelProperty(value = "端口号",required = true)
    @Value("${server.port}")
    private int port;

    @ApiModelProperty(value = "用户名",required = true)
    @Value("${spring.security.user.name}")
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @Value("${spring.security.user.password}")
    private String password;

    @ApiModelProperty(value = "系统时间")
    private String time;

}
