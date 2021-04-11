package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.pojo.Crontab;
import cc.sgee.visualoperation.common.pojo.WebSite;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.SaveSystemSettingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Thorn
 * @Date: 2021/3/1 10:11
 * @Blog: https://www.sgee.cc
 * @Description: 保存系统设置
 */
@Api(tags = "保存系统设置")
@RestController
@RequestMapping("/save")
public class SaveSystemSettingsController {

    @Autowired
    private SaveSystemSettingsService saveSystemSettingsService;

    @ApiOperation(value = "保存系统设置",notes = "保存面板名、端口号、用户名、密码")
    @RequestMapping(value = "/settings",method = RequestMethod.POST)
    public Result SaveSettings(@RequestBody WebSite webSite){
        try {
            saveSystemSettingsService.saveSettings(webSite.getPort(),webSite.getName(),webSite.getUsername(),
                    webSite.getPassword());
            return new Result(true,MessageConstant.SAVE_PANEL_SETTINGS_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.SAVE_PANEL_SETTINGS_FAIL);
        }
    }

    @ApiOperation(value = "关闭面板",notes = "关闭面板的所有访问权限")
    @RequestMapping(value = "/close_panel",method = RequestMethod.POST)
    public Result ClosePanel(){
        try {
            saveSystemSettingsService.closePanel();
            return new Result(true,MessageConstant.CLOSE_PANEL_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.CLOSE_PANEL_FAIL);
        }
    }

    @ApiOperation(value = "开启面板",notes = "开启面板的所有访问权限")
    @RequestMapping(value = "/open_panel",method = RequestMethod.POST)
    public void OpenPanel(HttpServletResponse response){
        saveSystemSettingsService.openPanel();
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().print("<h1>开启成功，正在跳转请稍后~</h1>");
            Thread.sleep(3000);
            response.sendRedirect("/");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "保存任务",notes = "将命令保存到Crontab")
    @RequestMapping(value = "/task",method = RequestMethod.POST)
    public Result SaveTask(@RequestBody Crontab crontab){
        try {
            saveSystemSettingsService.saveTask(crontab);
            return new Result(true,MessageConstant.SAVE_TASK_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.SAVE_TASK_FAIL);
        }
    }
}
