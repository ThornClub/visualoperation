package cc.sgee.visualoperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VisualoperationApplication {
    private static ConfigurableApplicationContext context;
    private static String[] args;
    public static void main(String[] args) {
        VisualoperationApplication.args = args;
        VisualoperationApplication.context = SpringApplication.run(VisualoperationApplication.class, args);
    }
    public static void restart() {
        //关闭应用上下文
        context.close();
        //重新启动项目
        VisualoperationApplication.context = SpringApplication.run(VisualoperationApplication.class, args);
    }
}
