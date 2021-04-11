package cc.sgee.visualoperation.schedul;

import cc.sgee.visualoperation.schedul.Quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author: Thorn
 * @Date: 2021/3/9 17:50
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Component
public class QuartzInit implements ApplicationRunner {
    @Autowired
    private Quartz quartz;
    @Value("${scheduling.enabled}")
    private boolean status;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (status){
            quartz.startCron();
        }
    }
}
