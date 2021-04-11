package cc.sgee.visualoperation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: Thorn
 * @Date: 2020/11/30 23:54
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                // swagger start
                .antMatchers("/swagger-ui.html")
                .antMatchers( "/swagger-resources/**")
                .antMatchers("/v2/api-docs")
                .antMatchers("configuration/ui")
                .antMatchers("configuration/security")
                .antMatchers("/webjars/**")
        // swagger end
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/images/**","/logout_success").permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                //指定登录页的路径
                .loginPage("/login")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/login")
                .failureUrl("/login_error")
                .defaultSuccessUrl("/")
                //必须允许所有用户访问我们的登录页（例如未验证的用户，否则验证流程就会进入死循环）
                //这个formLogin().permitAll()方法允许所有用户基于表单登录访问/login这个page。
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1);
        //默认都会产生一个hiden标签 里面有安全相关的验证 防止请求伪造 这边我们暂时不需要 可禁用掉
        http .csrf().disable();
        http .logout().logoutUrl("/logout").logoutSuccessUrl("/logout_success").permitAll();

    }

}
