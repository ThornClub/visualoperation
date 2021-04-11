package cc.sgee.visualoperation.service;

import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/3/7 15:56
 * @Blog: https://www.sgee.cc
 * @Description:
 */
public interface ScafetyService {
    List<Map<String, Object>> GetInfo();

    void OpenSSH();

    void CloseSSH();

    void ClosePing();

    void OpenPing();

    void ChangePort(String port);

    void BlockIP(String ip);

    void AllowPort(String port);

    void Delete(String havior);
}
