package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.pojo.SystemInfo;
import cc.sgee.visualoperation.common.pojo.WebSite;
import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.common.utils.GetSystemInfoUtils;
import cc.sgee.visualoperation.service.GetSystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2020/12/3 09:38
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Service
public class GetSystemInfoServiceImpl implements GetSystemInfoService {

    @Autowired
    private GetSystemInfoUtils sysUtils;

    @Autowired
    private WebSite webSite;

    @Autowired
    private SystemInfo systemInfo;

    @Override
    public List<Map> GetFixInfo() {
        List<Map> info = new ArrayList<>();
        Map<String,String> map_info = new HashMap<>();
        String sysinfo = ExecuteShell.GetResult("./sh/GetFixInfo.sh");
        map_info.put("ip",sysinfo.split(" ")[0]);
        map_info.put("version",sysinfo.split(" ")[1]);
        map_info.put("day",sysinfo.split(" ")[2]);
        info.add(map_info);
        return info;
    }

    @Override
    public List<Map> GetAll() {
        List<Map> info = new ArrayList<>();
        Map<String,String> map_info = new HashMap<>();
        String sysinfo = ExecuteShell.GetResult("./sh/GetNet.sh");
        String ram = sysUtils.getMemory().get("all");
        String remainram = sysUtils.getMemory().get("use");
        String usageram = sysUtils.getMemory().get("rate");
        map_info.put("time",sysinfo.split(" ")[0]);
        map_info.put("NIC",sysinfo.split(" ")[1]);
        map_info.put("download",sysinfo.split(" ")[2]);
        map_info.put("upload",sysinfo.split(" ")[3]);
        //判断负载状态是否正常，保留两位小数
        double load = sysUtils.getLoad();
        int core = sysUtils.getCpuCore();
        double loadRate = load / (core * 2) * 100;
        systemInfo.setLoad(loadRate);
        String loadStatus = (loadRate >= 90) ? "负载过高" : "负载正常";
        map_info.put("loadStatus",loadStatus);
        map_info.put("NumCores", String.valueOf(core));
        map_info.put("load",String.valueOf(new DecimalFormat("#.00").format(loadRate)));
        map_info.put("ram",ram);
        map_info.put("remainram", remainram);
        //获取根目录大小
        map_info.put("rom",sysUtils.getDisk().get("rom"));
        map_info.put("usedrom",sysUtils.getDisk().get("usedrom"));
        map_info.put("romusage",sysUtils.getDisk().get("romusage"));
        map_info.put("usageram",usageram);
        systemInfo.setDisk(Double.parseDouble(sysUtils.getDisk().get("romusage")));
        systemInfo.setMemory(Double.parseDouble(usageram.replace("%","")));
        info.add(map_info);
        return info;
    }

    @Override
    public String GetCpu() throws InterruptedException {
        return sysUtils.getCpuRate();
    }

    @Override
    public List<Map> settings() {
        List<Map> info = new ArrayList<>();
        Map<String,String> map_info = new HashMap<>();
        String date = ExecuteShell.Shell("date");
        map_info.put("time",date);
        map_info.put("name",webSite.getName());
        map_info.put("port", String.valueOf(webSite.getPort()));
        map_info.put("username",webSite.getUsername());
        map_info.put("password",webSite.getPassword());
        info.add(map_info);
        return info;
    }
}
