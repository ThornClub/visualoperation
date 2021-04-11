package cc.sgee.visualoperation;

import cc.sgee.visualoperation.schedul.Quartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class VisualoperationApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisualoperationApplication.class, args);
    }

}
