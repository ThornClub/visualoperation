package cc.sgee.visualoperation.common.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author: Thorn
 * @Date: 2021/3/5 01:00
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Data
@RefreshScope
@Component
public class MonitorSettings {
    @Value("${monitor.monitorSetting.cpu}")
    private double cpu;

    @Value("${monitor.monitorSetting.load}")
    private double load;

    @Value("${monitor.monitorSetting.memory}")
    private double memory;

    @Value("${monitor.monitorSetting.disk}")
    private double disk;
}
