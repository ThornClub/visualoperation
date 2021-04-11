package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.pojo.MonitorEmailReceive;
import cc.sgee.visualoperation.common.pojo.MonitorEmailSend;
import cc.sgee.visualoperation.common.pojo.MonitorSettings;
import cc.sgee.visualoperation.schedul.Quartz;
import cc.sgee.visualoperation.common.utils.YmlUtil;
import cc.sgee.visualoperation.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author: Thorn
 * @Date: 2021/3/5 00:50
 * @Blog: https://www.sgee.cc
 * @Description: Monitor服务实现类
 */
@Service
@RefreshScope
public class MonitorServiceImpl implements MonitorService {

    private static final File yml = new File("application.yml");

    @Qualifier("configDataContextRefresher")
    @Autowired
    private ContextRefresher contextRefresher;

    @Autowired
    private MonitorEmailReceive emailReceive;
    @Autowired
    private MonitorEmailSend emailSend;
    @Autowired
    private MonitorSettings settings;
    @Value("${monitor.remind.wx.key}")
    private String wx_key;
    @Value("${scheduling.enabled}")
    private boolean monitor_status;
    @Autowired
    private Quartz quartz;

    @Override
    public List<Map> GetInfo() {
        List<Map> info = new ArrayList<>();
        Map<String,Object> map_info = new HashMap<>();
        map_info.put("wx_key",wx_key);
        map_info.put("cpu",settings.getCpu());
        map_info.put("load",settings.getLoad());
        map_info.put("memory",settings.getMemory());
        map_info.put("disk",settings.getDisk());
        map_info.put("email_rec",emailReceive.getReceive());
        map_info.put("send_email",emailSend.getSend_email());
        map_info.put("smtp_password",emailSend.getSmtp_password());
        map_info.put("smtp_server",emailSend.getSmtp_server());
        map_info.put("smtp_port",emailSend.getSmtp_port());
        map_info.put("status",monitor_status);
        info.add(map_info);
        return info;
    }

    @Override
    public void CloseMonitor() {
        try {
            quartz.stopCron();
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("scheduling.enabled",false);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OpenMonitor() {
        try {
            quartz.startCron();
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("scheduling.enabled",true);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SaveMonitorData(MonitorSettings monitorSettings) {
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("monitor.monitorSetting.cpu",monitorSettings.getCpu());
            YmlUtil.saveOrUpdateByKey("monitor.monitorSetting.load",monitorSettings.getLoad());
            YmlUtil.saveOrUpdateByKey("monitor.monitorSetting.memory",monitorSettings.getMemory());
            YmlUtil.saveOrUpdateByKey("monitor.monitorSetting.disk",monitorSettings.getDisk());
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SaveWxKey(String wx_key) {
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("monitor.remind.wx.key",wx_key);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AddReceive(String email) {
        emailReceive.getReceive().add(email);
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("monitor.remind.email.receive",emailReceive.getReceive());
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void SaveSendPeople(MonitorEmailSend emailSend) {
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("spring.mail.username",emailSend.getSend_email());
            YmlUtil.saveOrUpdateByKey("spring.mail.password",emailSend.getSmtp_password());
            YmlUtil.saveOrUpdateByKey("spring.mail.host",emailSend.getSmtp_server());
            YmlUtil.saveOrUpdateByKey("spring.mail.port",emailSend.getSmtp_port());
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteReceive(String email) {
        try {
            emailReceive.getReceive().remove(email);
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("monitor.remind.email.receive",emailReceive.getReceive());
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
