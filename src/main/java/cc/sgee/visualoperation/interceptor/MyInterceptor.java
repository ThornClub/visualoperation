package cc.sgee.visualoperation.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Thorn
 * @Date: 2021/3/3 22:56
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Component
@RefreshScope
public class MyInterceptor implements HandlerInterceptor {

    @Value("${website.status}")
    private boolean Panel_status;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!Panel_status){
            response.sendRedirect("/close");
            return false;
        }
        return true;
    }
}
