package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.pojo.MonitorSettings;
import cc.sgee.visualoperation.common.pojo.SystemInfo;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.GetSystemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Thorn
 * @Date: 2020/12/3 09:36
 * @Blog: https://www.sgee.cc
 * @Description: 获取系统信息
 */
@Api(tags = "系统信息")
@RestController
@RequestMapping("/systeminfo")
public class GetSystemInfoController {

    @Autowired
    private MonitorSettings monitorSettings;
    @Autowired
    private SystemInfo systemInfo;

    @Autowired
    private GetSystemInfoService getSystemInfoService;
    //获取基本信息
    @ApiOperation(value = "基本信息",notes = "获取服务器公网IP、版本号、启动时间")
    @RequestMapping(value = "/fixinfo",method = RequestMethod.POST)
    public Result GetFinInfo(){
        try {
            return new Result(true,"",getSystemInfoService.GetFixInfo());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_SYSTEM_VERSION_FAIL);
        }
    }
    //获取服务器内存、储存、流量、CPU信息
    @ApiOperation(value = "内存/存储/流量",notes = "获取服务器内存、储存、流量信息")
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    public Result GetAll(){
        try {
            return new Result(true,"",getSystemInfoService.GetAll());
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_ALL_FAIL);
        }
    }
    //获取cpu使用率
    @ApiOperation(value = "CPU使用率",notes = "获取服务器CPU使用率")
    @RequestMapping(value = "/cpu",method = RequestMethod.POST)
    public Result GetCpu(){
        try {
            return new Result(true,"",getSystemInfoService.GetCpu());
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_CPU_FAIL);
        }
    }
    //获取面板设置
    @ApiOperation(value = "面板设置",notes = "获取面板端口、用户名、密码")
    @RequestMapping(value = "/settings",method = RequestMethod.POST)
    public Result SystemTime(){
        try {
            return new Result(true,"",getSystemInfoService.settings());
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_TIME_FAIL);
        }
    }
}
