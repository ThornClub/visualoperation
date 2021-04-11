package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.pojo.ScafetyAllowPort;
import cc.sgee.visualoperation.common.pojo.ScafetyBlockIP;
import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.common.utils.TestPort;
import cc.sgee.visualoperation.common.utils.YmlUtil;
import cc.sgee.visualoperation.service.ScafetyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Thorn
 * @Date: 2021/3/7 15:57
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Service
@RefreshScope
public class ScafetyServiceImpl implements ScafetyService {

    private static final File yml = new File("application.yml");

    @Qualifier("configDataContextRefresher")
    @Autowired
    private ContextRefresher contextRefresher;


    @Override
    public List<Map<String, Object>> GetInfo() {
        try {
            //获得ssh端口
            List<Map<String,Object>> info = new ArrayList<>();
            Map<String,Object> map_info = new HashMap<>();
            String[] cmd_port = {"/bin/sh","-c","netstat -ntlp | awk '!a[$NF]++ && $NF~/sshd$/{sub (\".*:\",\"\",$4);" +
                    "print $4}'"};
            String port = ExecuteShell.Shell(cmd_port);
            map_info.put("port",port);
            //禁Ping状态
            boolean Ping_status = false;
            String[] cmd = {"/bin/sh","-c","firewall-cmd --query-rich-rule='rule protocol value='icmp' drop'"};
            String status = ExecuteShell.Shell(cmd);
            if (status.equals("yes")){
                Ping_status = true;
            }
            map_info.put("ping_status",Ping_status);
            //ssh状态
            boolean SSH_status = false;
            String ssh_status = ExecuteShell.Shell("systemctl is-active sshd");
            if (ssh_status.equals("active"))
            {
                SSH_status = true;
            }
            map_info.put("ssh_status",SSH_status);
            //获取已开放的端口
            String result = ExecuteShell.Shell("firewall-cmd --zone=public --list-ports");
            List<String> PublicPort = new ArrayList<>();
            //切割获取的字符串
            String[] resule_split = result.split(" ");
            for (String s : resule_split) {
                String[] split = s.split("/");
                PublicPort.add(split[0]);
            }
            List<Map<String, String>> list = ScafetyAllowPort.getAllowPort();
            if (PublicPort != null && list != null) {
                List<String> already_port = new ArrayList<>();
                for (Map<String, String> stringStringMap : list) {
                    already_port.add(stringStringMap.get("port"));
                }
                if (already_port.size() <= PublicPort.size()) {
                    PublicPort.removeAll(already_port);
                    for (String s : PublicPort) {
                        Map<String, String> map = new HashMap<>();
                        map.put("port", s);
                        map.put("havior", "放行端口：" + s);
                        if (TestPort.test(s)) {
                            map.put("status", "正常");
                        } else {
                            map.put("status", "未使用");
                        }
                        map.put("time", "1970-01-01 00:00:00");
                        list.add(map);
                    }
                }
            }
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("scafety.allowPort",list);
            new Thread(() -> contextRefresher.refresh()).start();
            //获取配置文件中的blockIP
            List<Map<String, String>> ipList = ScafetyBlockIP.getBlockIP();
            for (int i = 0; i < ipList.size(); i++) {
                String ip = ipList.get(i).get("ip");
                String[] cmds = {"/bin/sh","-c","firewall-cmd --query-rich-rule=\"rule family='ipv4' source " +
                        "address='"+ip+"' reject\""};
                String IPresult = ExecuteShell.Shell(cmds);
                if (IPresult.equals("no"))
                {
                    ipList.remove(i);
                }
            }
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("scafety.blockIP",ipList);
            new Thread(() -> contextRefresher.refresh()).start();
            //合并集合
            List<Map<String,String>> IPAndPort = new ArrayList<>();
            IPAndPort.addAll(list);
            IPAndPort.addAll(ipList);
            map_info.put("table",IPAndPort);
            info.add(map_info);
            return info;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void OpenSSH() {
        ExecuteShell.Shell("systemctl start sshd");
    }

    @Override
    public void CloseSSH() {
        ExecuteShell.Shell("systemctl stop sshd");
    }

    @Override
    public void ClosePing() {
        String[] cmd = {"/bin/sh","-c","firewall-cmd --permanent --add-rich-rule='rule protocol value=icmp drop'"};
        ExecuteShell.Shell(cmd);
    }

    @Override
    public void OpenPing() {
        String[] cmd = {"/bin/sh","-c","firewall-cmd --permanent --remove-rich-rule='rule protocol value=icmp drop'"};
        ExecuteShell.Shell(cmd);
    }

    @Override
    public void ChangePort(String port) {
        ExecuteShell.GetResult("/root/ChangeSSHPort.sh " + port);
    }

    @Override
    public void BlockIP(String ip) {
        String[] cmd = {"/bin/sh","-c","firewall-cmd --permanent --add-rich-rule=\"rule family='ipv4' source " +
                "address='"+ip+"' reject\""};
        ExecuteShell.Shell(cmd);
        ExecuteShell.Shell("firewall-cmd --reload");
        List<Map<String, String>> ipList = ScafetyBlockIP.getBlockIP();
        Map<String,String> map = new HashMap<>();
        map.put("ip",ip);
        map.put("havior","屏蔽IP:"+ip);
        map.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("status","正常");
        ipList.add(map);
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("scafety.blockIP",ipList);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AllowPort(String port) {
        String[] cmd = {"/bin/sh","-c","firewall-cmd --zone=public --add-port=" + port +"/tcp --permanent"};
        ExecuteShell.Shell(cmd);
        ExecuteShell.Shell("firewall-cmd --reload");
        List<Map<String, String>> portList = ScafetyAllowPort.getAllowPort();
        Map<String,String> map = new HashMap<>();
        map.put("port",port);
        map.put("havior","放行端口："+port);
        map.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (TestPort.test(port)){
            map.put("status","正常");
        }
        else {
            map.put("status","未使用");
        }
        portList.add(map);
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("scafety.allowPort",portList);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Delete(String havior) {
        List<Map<String, String>> ipList = ScafetyBlockIP.getBlockIP();
        List<Map<String, String>> portList = ScafetyAllowPort.getAllowPort();
        if (havior.substring(0,2).equals("屏蔽")){
            for (int i = 0; i < ipList.size(); i++) {
                if (ipList.get(i).get("havior").equals(havior)){
                    String[] cmd = {"/bin/sh","-c","firewall-cmd --remove-rich-rule=\"rule family='ipv4' source " +
                            "address='"+ipList.get(i).get("ip")+"' reject\""};
                    ExecuteShell.Shell(cmd);
                    ExecuteShell.Shell("firewall-cmd --reload");
                    ipList.remove(i);
                    try {
                        YmlUtil.setYmlFile(yml);
                        YmlUtil.saveOrUpdateByKey("scafety.blockIP",ipList);
                        new Thread(() -> contextRefresher.refresh()).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < portList.size(); i++) {
                if (portList.get(i).get("havior").equals(havior)){
                    String[] cmd = {"/bin/sh","-c","firewall-cmd --zone=public --remove-port="+portList.get(i).get(
                            "port")+
                            "/tcp " +
                            "--permanent"};
                    ExecuteShell.Shell(cmd);
                    ExecuteShell.Shell("firewall-cmd --reload");
                    portList.remove(i);
                    try {
                        YmlUtil.setYmlFile(yml);
                        YmlUtil.saveOrUpdateByKey("scafety.allowPort",portList);
                        new Thread(() -> contextRefresher.refresh()).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
