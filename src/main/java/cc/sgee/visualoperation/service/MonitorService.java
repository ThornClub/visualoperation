package cc.sgee.visualoperation.service;

import cc.sgee.visualoperation.common.pojo.MonitorEmailSend;
import cc.sgee.visualoperation.common.pojo.MonitorSettings;

import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/3/5 00:50
 * @Blog: https://www.sgee.cc
 * @Description:
 */
public interface MonitorService {
    List<Map> GetInfo();

    void CloseMonitor();

    void OpenMonitor();

    void SaveMonitorData(MonitorSettings monitorSettings);

    void SaveWxKey(String wx_key);

    void AddReceive(String email);

    void SaveSendPeople(MonitorEmailSend emailSend);

    void DeleteReceive(String email);
}
