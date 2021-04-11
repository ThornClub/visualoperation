package cc.sgee.visualoperation.common.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/3/7 16:12
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Repository
@ConfigurationProperties(prefix = "scafety")
public class ScafetyAllowPort {
    private static List<Map<String,String>> allowPort;

    public static List<Map<String, String>> getAllowPort() {
        return allowPort;
    }

    public void setAllowPort(List<Map<String, String>> allowPort) {
        ScafetyAllowPort.allowPort = allowPort;
    }
}
