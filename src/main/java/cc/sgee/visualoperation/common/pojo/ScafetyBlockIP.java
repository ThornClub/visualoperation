package cc.sgee.visualoperation.common.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/3/8 01:51
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Repository
@ConfigurationProperties(prefix = "scafety")
public class ScafetyBlockIP {
    private static List<Map<String,String>> blockIP;

    public static List<Map<String, String>> getBlockIP() {
        return blockIP;
    }

    public void setBlockIP(List<Map<String, String>> blockIP) {
        ScafetyBlockIP.blockIP = blockIP;
    }
}
