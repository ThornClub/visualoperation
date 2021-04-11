package cc.sgee.visualoperation.service;

import cc.sgee.visualoperation.common.pojo.Crontab;

/**
 * @author: Thorn
 * @Date: 2021/3/1 10:22
 * @Blog: https://www.sgee.cc
 * @Description:
 */
public interface SaveSystemSettingsService {
    void saveSettings(int port, String name, String username, String password);

    void closePanel();

    void openPanel();

    void saveTask(Crontab crontab);
}
