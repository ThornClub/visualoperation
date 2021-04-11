package cc.sgee.visualoperation.config;

import cc.sgee.visualoperation.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Thorn
 * @Date: 2021/3/3 23:13
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Component
public class MyWebMvcConfig implements WebMvcConfigurer {

    /*
     * Description:〈将自定义拦截器作为Bean写入配置〉
     * @param
     * @return: cc.sgee.visualoperation.interceptor.MyInterceptor
     */
    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/close")
                .excludePathPatterns("/save/open_panel")
                .excludePathPatterns("/open_panel_form")
                .excludePathPatterns("/open_panel");

    }
}
