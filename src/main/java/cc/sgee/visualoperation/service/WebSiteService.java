package cc.sgee.visualoperation.service;

import java.util.List;
import java.util.Map;

/**
 * @program: visualoperation
 * @description: 网站页面服务接口
 * @author: Thorn
 * @create: 2021-05-07 21:20
 **/
public interface WebSiteService {
    List<Map<String,String>> getInfo();
    void operate(String operate);
    void add(String domain, String port);
    List<Map<String,String>> getWebInfo();
    void del(String domain, String root);
}
