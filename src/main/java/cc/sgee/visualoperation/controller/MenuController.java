package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.pojo.User;
import cc.sgee.visualoperation.common.pojo.WebSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Thorn
 * @Date: 2021/1/29 21:02
 * @Blog: https://www.sgee.cc
 * @Description: 页面导航
 */
@Controller
public class MenuController {
    /*
        首页
     */
    @RequestMapping("/home")
    public String home(){
        return "status";
    }

    /*
        数据库界面
     */
    @RequestMapping("/database")
    public String database() {
        return "database";
    }

    /*
        网站页面
     */
    @RequestMapping("/website")
    public String website() {
        return "website";
    }

    /*
        监控页面
    */
    @RequestMapping("/monitor")
    public String monitor() {
        return "monitor";
    }

    /*
        安全页面
    */
    @RequestMapping("/scafety")
    public String scafety() {
        return "scafety";
    }

    /*
        任务页面
    */
    @RequestMapping("/tasks")
    public String tasks() {
        return "tasks";
    }

    /*
        设置页面
    */
    @RequestMapping("/settings")
    public String settings() {
        return "settings";
    }
}
