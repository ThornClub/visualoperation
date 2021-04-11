package cc.sgee.visualoperation.common.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author: Thorn
 * @Date: 2021/3/5 01:07
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Getter
@Setter
@Repository
@ConfigurationProperties(prefix = "monitor.remind.email")
public class MonitorEmailReceive {
    private List<String> receive;
}
