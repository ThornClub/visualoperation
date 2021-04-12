package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.service.DatabaseService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/4/12 08:26
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Override
    public List<Map<String, String>> getInfo() {
        List<Map<String,String>> info = new ArrayList<>();
        Map<String,String> mapInfo = new HashMap<>();
        String result = ExecuteShell.GetResult("./GetMysqlInfo.sh");
        String mysqlStatus;
        if ("0".equals(result.split(" ")[0]))
        {
            mysqlStatus = "Running";
        }
        else
        {
            mysqlStatus = "Stopped";
        }
        mapInfo.put("mysqlStatus",mysqlStatus);
        mapInfo.put("mysqlVersion",result.split(" ")[1]);
        info.add(mapInfo);
        return info;
    }

    @Override
    public void operate(String operate) {
        switch (operate){
            case "Start":
                ExecuteShell.Shell("service mysqld start");
                break;
            case "Stop":
                ExecuteShell.Shell("service mysqld stop");
                break;
            case "Restart":
                ExecuteShell.Shell("service mysqld restart");
                break;
            default:
        }
    }
}
