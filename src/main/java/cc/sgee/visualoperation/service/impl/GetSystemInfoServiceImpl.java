package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.pojo.SystemInfo;
import cc.sgee.visualoperation.common.pojo.WebSite;
import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.service.GetSystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author: Thorn
 * @Date: 2020/12/3 09:38
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Service
public class GetSystemInfoServiceImpl implements GetSystemInfoService {

    @Autowired
    private WebSite webSite;

    @Autowired
    private SystemInfo systemInfo;

    @Override
    public List<Map> GetFixInfo() {
        List<Map> info = new ArrayList<>();
        Map<String,String> map_info = new HashMap<>();
        String sysinfo = ExecuteShell.GetResult("./GetFixInfo.sh");
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
        String sysinfo = ExecuteShell.GetResult("./GetNet.sh");
        double ram = 0;
        if (sysinfo.split(" ")[6] != null) {
            ram = Double.parseDouble(sysinfo.split(" ")[6]);
        }
        double remainram = 0;
        if (sysinfo.split(" ")[7] != null) {
            remainram = Double.parseDouble(sysinfo.split(" ")[7]);
        }
        double usageram = ((ram - remainram) / ram ) * 100;
        map_info.put("time",sysinfo.split(" ")[0]);
        map_info.put("NIC",sysinfo.split(" ")[1]);
        map_info.put("download",sysinfo.split(" ")[2]);
        map_info.put("upload",sysinfo.split(" ")[3]);
        map_info.put("NumCores",sysinfo.split(" ")[5]);
        //判断负载状态是否正常，保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        double load = Double.parseDouble(sysinfo.split(" ")[4]);
        int core = Integer.parseInt(sysinfo.split(" ")[5]);
        String loadStatus = (load > core * 3) ? "负载过高" : "负载正常";
        map_info.put("loadStatus",loadStatus);
        map_info.put("load",String.valueOf(df.format(load / (core * 3) * 100)));
        map_info.put("ram", String.valueOf(ram));
        map_info.put("remainram", String.valueOf(remainram));
        map_info.put("rom",sysinfo.split(" ")[8]);
        map_info.put("usedrom",sysinfo.split(" ")[9]);
        map_info.put("romusage",sysinfo.split(" ")[10]);
        if (sysinfo.split(" ")[10] != null ) {
            systemInfo.setDisk(Integer.parseInt(sysinfo.split(" ")[10]));
        }
        systemInfo.setMemory(usageram);
        if (sysinfo.split(" ")[4] != null && sysinfo.split(" ")[5] != null) {
            systemInfo.setLoad(load / (core * 3) * 100);
        }
        info.add(map_info);
        return info;
    }

    @Override
    public String GetCpu() {
        String cpu = ExecuteShell.GetResult("./cpu.sh");
        if (!"".equals(cpu)) {
            systemInfo.setCpu(Double.parseDouble(cpu));
        }
        return cpu;
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
