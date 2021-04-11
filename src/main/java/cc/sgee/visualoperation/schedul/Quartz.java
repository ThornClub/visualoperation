package cc.sgee.visualoperation.schedul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: Thorn
 * @Date: 2021/3/7 01:33
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Component
public class Quartz {
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> future;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void startCron() {
        future = taskScheduler.schedule(new MyTask(),
                triggerContext -> new CronTrigger("0 0/5 * * * *").nextExecutionTime(triggerContext));
    }

    public void stopCron() {
        if (future != null) {
            future.cancel(true);
        }
    }
}
