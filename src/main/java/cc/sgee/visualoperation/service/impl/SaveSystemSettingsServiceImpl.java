package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.pojo.Crontab;
import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.common.utils.YmlUtil;
import cc.sgee.visualoperation.service.SaveSystemSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author: Thorn
 * @Date: 2021/3/1 10:23
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Service
public class SaveSystemSettingsServiceImpl implements SaveSystemSettingsService {

    private static final File yml = new File("application.yml");

    @Qualifier("configDataContextRefresher")
    @Autowired
    private ContextRefresher contextRefresher;

    @Override
    public void saveSettings(int port, String name, String username, String password) {
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("server.port",port);
            YmlUtil.saveOrUpdateByKey("spring.security.user.name",username);
            YmlUtil.saveOrUpdateByKey("spring.security.user.password",password);
            YmlUtil.saveOrUpdateByKey("website.name",name);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closePanel() {
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("website.status",false);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openPanel() {
        try {
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("website.status",true);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTask(Crontab crontab) {
        try {
            //将命令写入文件
            BufferedWriter bf = new BufferedWriter(new FileWriter("cron"));
            bf.write(crontab.getDesc());
            bf.newLine();
            bf.write(crontab.getCrontab());
            bf.newLine();
            bf.flush();
            bf.close();
            //将命令添加至crontab
            ExecuteShell.Shell("crontab cron");
            //删除写入的文件
            Thread.sleep(5000);
            ExecuteShell.Shell("rm -rf cron");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
