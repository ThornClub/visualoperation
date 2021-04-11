package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.pojo.MonitorEmailReceive;
import cc.sgee.visualoperation.common.pojo.MonitorEmailSend;
import cc.sgee.visualoperation.common.pojo.MonitorSettings;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.MonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Thorn
 * @Date: 2021/3/5 00:48
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Api(tags = "监控报警")
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @ApiOperation(value = "获取配置信息",notes = "获取监控报警的配置信息")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public Result GetInfo(){
        try {
            return new Result(true,"",monitorService.GetInfo());
        } catch (Exception e) {
            return new Result(false,"监控报警信息获取失败,请重试");
        }
    }

    @ApiOperation(value = "开关监控",notes = "开启/关闭资源监控，随时报警")
    @RequestMapping(value = "/switch/{monitor_status}",method = RequestMethod.POST)
    public Result OpenMonitor(@PathVariable boolean monitor_status){
        if (monitor_status){
            try {
                monitorService.OpenMonitor();
                return new Result(true,MessageConstant.OPEN_MONITOR_SUCCESS);
            } catch (Exception e) {
                return new Result(false,MessageConstant.OPEN_MONITOR_ERROR);
            }
        }
        else {
            try {
                monitorService.CloseMonitor();
                return new Result(true, MessageConstant.CLOSS_MONITOR_SUCCESS);
            } catch (Exception e) {
                return new Result(false,MessageConstant.CLOSS_MONITOR_ERROR);
            }
        }
    }

    @ApiOperation(value = "保存监控数据",notes = "保存CPU、负载、内存、硬盘监测值")
    @RequestMapping(value = "/save_data",method = RequestMethod.POST)
    public Result SaveMonitorData(@RequestBody MonitorSettings monitorSettings){
        try {
            monitorService.SaveMonitorData(monitorSettings);
            return new Result(true,MessageConstant.SAVE_MONITOR_DATA_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.SAVE_MONITOR_DATA_FAIL);
        }
    }

    @ApiOperation(value = "保存Server酱Key",notes = "保存微信通知Server酱的key")
    @RequestMapping(value = "/savewx/{wx_key}",method = RequestMethod.POST)
    public Result SaveWxKey(@PathVariable String wx_key){
        try {
            monitorService.SaveWxKey(wx_key);
            return new Result(true,MessageConstant.SAVE_WX_KEY_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.SAVE_WX_KEY_FAIL);
        }
    }

    @ApiOperation(value = "添加收件人",notes = "添加email通知收件人")
    @RequestMapping(value = "/addReceive/{email}",method = RequestMethod.POST)
    public Result AddReceive(@PathVariable String email){
        try {
            monitorService.AddReceive(email);
            return new Result(true,MessageConstant.ADD_RECEIVE_PEOPLE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_RECEIVE_PEOPLE_FAIL);
        }
    }

    @ApiOperation(value = "保存发件人设置",notes = "保存发信人基本信息")
    @RequestMapping(value = "/saveSendPeople",method = RequestMethod.POST)
    public Result SaveSendPeople(@RequestBody MonitorEmailSend emailSend){
        try {
            monitorService.SaveSendPeople(emailSend);
            return new Result(true,MessageConstant.SAVE_SEND_SETTING_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.SAVE_SEND_SETTING_FAIL);
        }
    }

    @ApiOperation(value = "删除收件人",notes = "删除收件人邮箱")
    @RequestMapping(value = "/delReceive/{email}",method = RequestMethod.POST)
    public Result DeleteReceive(@PathVariable String email){
        try {
            monitorService.DeleteReceive(email);
            return new Result(true,MessageConstant.DELETE_RECEIVE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_RECEIVE_FAIL);
        }
    }
}
