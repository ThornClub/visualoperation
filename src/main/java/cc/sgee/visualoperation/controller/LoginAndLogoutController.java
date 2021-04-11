package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.pojo.User;
import cc.sgee.visualoperation.common.pojo.WebSite;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: Thorn
 * @Date: 2020/12/1 10:14
 * @Blog: https://www.sgee.cc
 * @Description: 登陆退出页面跳转
 */
@Controller
public class LoginAndLogoutController {
    @Autowired
    private WebSite webSite;
    @Autowired
    private User user;

    @RequestMapping("/login_error")
    public String login_error(Model model){
        model.addAttribute("login_error","用户名或密码错误");
        return "login";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("name",webSite.getName());
        System.out.println("jenkins成功运行了");
        return "main";
    }

    @RequestMapping("/logout_success")
    public String logout(Model model){
        model.addAttribute("login_error", "注销成功");
        return "login";
    }

    @RequestMapping("/close")
    public String close(){
        return "close";
    }

    @RequestMapping("/open_panel_form")
    public String open_panel_form(){
        return "open_panel";
    }

    @RequestMapping("/open_panel")
    public void open_panel(@RequestParam String username, String password, HttpServletRequest request,
                           HttpServletResponse response){
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
            try {
                request.getRequestDispatcher("/save/open_panel").forward(request,response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            request.setAttribute("msg","账号或密码错误");
            try {
                request.getRequestDispatcher("/open_panel_form").forward(request,response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
