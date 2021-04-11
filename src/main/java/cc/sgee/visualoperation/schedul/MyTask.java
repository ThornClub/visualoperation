package cc.sgee.visualoperation.schedul;

import cc.sgee.visualoperation.common.pojo.MonitorEmailReceive;
import cc.sgee.visualoperation.common.pojo.MonitorSettings;
import cc.sgee.visualoperation.common.pojo.SystemInfo;
import cc.sgee.visualoperation.common.utils.ApplicationContextProvider;
import cc.sgee.visualoperation.common.utils.NoticeUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Thorn
 * @Date: 2021/3/7 01:36
 * @Blog: https://www.sgee.cc
 * @Description: 定时任务
 */
public class MyTask implements Runnable{
    private SystemInfo systemInfo;

    private MonitorSettings monitorSettings;

    private MonitorEmailReceive receive;

    private NoticeUtils noticeUtils;

    //多线程不允许@AutoWird注入，使用工具类获取Bean
    public MyTask(){
        this.systemInfo = ApplicationContextProvider.getBean(SystemInfo.class);
        this.monitorSettings = ApplicationContextProvider.getBean(MonitorSettings.class);
        this.receive = ApplicationContextProvider.getBean(MonitorEmailReceive.class);
        this.noticeUtils = ApplicationContextProvider.getBean(NoticeUtils.class);
    }

    private void notice_text(String text){
        noticeUtils.SendWx(text);
        if (receive.getReceive().size() > 0) {
            for (int i = 0; i < receive.getReceive().size(); i++) {
                noticeUtils.SendEmail(receive.getReceive().get(i),text);
            }
        }
    }

    @Override
    public void run() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (systemInfo != null && monitorSettings != null)
        {
            if (systemInfo.getCpu() >= monitorSettings.getCpu()){
                notice_text("时间："+date+"  报警事件：CPU占用率已超过"+monitorSettings.getCpu()+"%,请谨慎操作,保证服务正常运行！");
            }
            if (systemInfo.getDisk() >= monitorSettings.getDisk()){
                notice_text("时间："+date+"  报警事件：磁盘占用率已超过"+monitorSettings.getDisk()+"%,请删除无用文件,保证服务正常运行！");
            }
            if (systemInfo.getLoad() >= monitorSettings.getLoad()){
                notice_text("时间："+date+"  报警事件：系统负载百分比已超过"+monitorSettings.getLoad()+"%,请谨慎操作,保证服务正常运行！");
            }
            if (systemInfo.getMemory() >= monitorSettings.getMemory()){
                notice_text("时间："+date+"  报警事件： 系统内存占用率已超过"+monitorSettings.getMemory()+"%,请尝试杀掉大内存应用,以保证系统正常运行");
            }
        }
     }
}
