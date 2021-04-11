package cc.sgee.visualoperation;

import cc.sgee.visualoperation.common.pojo.MonitorSettings;
import cc.sgee.visualoperation.schedul.MyTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;


@SpringBootTest
class VisualoperationApplicationTests {

    @Test
    void contextLoads() throws Exception{
    }

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void test1(){
        restTemplate.getForObject("https://sc.ftqq.com/SCU69038Tc47163fcf7b82a59188c79053967a00f5df71d8d1996b" +
                ".send?text=SpringBoot,Get请求测试",String.class);
    }

}