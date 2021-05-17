package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.service.WebSiteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: visualoperation
 * @description: 网站页面服务实现类
 * @author: Thorn
 * @create: 2021-05-07 21:21
 **/
@Service
public class WebSiteServiceImpl implements WebSiteService {
    @Override
    public List<Map<String, String>> getInfo() {
        List<Map<String,String>> info = null;
        try {
            info = new ArrayList<>();
            Map<String,String> mapInfo = new HashMap<>();
            String result = ExecuteShell.GetResult("./sh/GetNginxInfo.sh");
            String nginxStatus;
            if ("0".equals(result.split(" ")[0]))
            {
                nginxStatus = "Running";
            }
            else
            {
                nginxStatus = "Stopped";
            }
            mapInfo.put("nginxStatus",nginxStatus);
            mapInfo.put("nginxVersion",result.split(" ")[1]);
            info.add(mapInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    @Override
    public void operate(String operate) {
        switch (operate){
            case "Start":
                ExecuteShell.Shell("service nginx start");
                break;
            case "Stop":
                ExecuteShell.Shell("service nginx stop");
                break;
            case "Restart":
                ExecuteShell.Shell("service nginx restart");
                break;
            default:
        }
    }

    @Override
    public void add(String domain, String port) {
        try {
            ExecuteShell.Shell("./sh/AddWeb.sh " + domain + " " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, String>> getWebInfo() {
        List<Map<String,String>> info = new ArrayList<>();
        String result = ExecuteShell.GetResult("./sh/GetWebInfo.sh");
        String[] res = result.split(" ");
        for (int i = 0; i < res.length; i++) {
            Map<String,String> map_info = new HashMap<>();
            map_info.put("ssl",res[i].split(",")[0]);
            map_info.put("domain",res[i].split(",")[1]);
            map_info.put("port",res[i].split(",")[2]);
            map_info.put("root",res[i].split(",")[3]);
            info.add(map_info);
        }
        return info;
    }

    @Override
    public void del(String domain, String root) {
        ExecuteShell.Shell("./sh/delWeb.sh " + domain + " " + root);
    }
}
