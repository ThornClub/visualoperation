package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.pojo.ScafetyAllowPort;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.ScafetyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Thorn
 * @Date: 2021/3/7 15:52
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Api(tags = "安全操作")
@RestController
@RequestMapping("/scafety")
public class ScafetyController {

    @Autowired
    private ScafetyService scafetyService;

    @ApiOperation(value = "获得安全信息",notes = "获得安全信息及操作日志")
    @RequestMapping(value = "/getinfo",method = RequestMethod.POST)
    public Result GetInfo(){
        try {
            return new Result(true,"",scafetyService.GetInfo());
        } catch (Exception e) {
            return new Result(false,"");
        }
    }

    @ApiOperation(value = "开启SSH",notes = "开启SSH连接")
    @RequestMapping(value = "/open_ssh",method = RequestMethod.POST)
    public Result OpenSSH(){
        try {
            scafetyService.OpenSSH();
            return new Result(true, MessageConstant.OPEN_SSH_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.OPEN_SSH_FAIL);
        }
    }

    @ApiOperation(value = "关闭SSH",notes = "关闭SSH连接")
    @RequestMapping(value = "/close_ssh",method = RequestMethod.POST)
    public Result CloseSSH(){
        try {
            scafetyService.CloseSSH();
            return new Result(true, MessageConstant.CLOSE_SSH_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.CLOSE_SSH_FAIL);
        }
    }

    @ApiOperation(value = "关闭Ping",notes = "关闭Ping，防止受到攻击")
    @RequestMapping(value = "/close_ping",method = RequestMethod.POST)
    public Result ClosePing(){
        try {
            scafetyService.ClosePing();
            return new Result(true, MessageConstant.CLOSE_Ping_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.CLOSE_Ping_FAIL);
        }
    }

    @ApiOperation(value = "开启Ping",notes = "开启Ping，可以检测连通性")
    @RequestMapping(value = "/open_ping",method = RequestMethod.POST)
    public Result OpenPing(){
        try {
            scafetyService.OpenPing();
            return new Result(true, MessageConstant.OPEN_Ping_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.OPEN_Ping_FAIL);
        }
    }

    @ApiOperation(value = "修改端口",notes = "修改SSH端口")
    @RequestMapping(value = "/change_port/{port}",method = RequestMethod.POST)
    public Result ChangePort(@PathVariable String port){
        try {
            scafetyService.ChangePort(port);
            return new Result(true, MessageConstant.CHANGE_SSH_PORT_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.CHANGE_SSH_PORT_FAIL);
        }
    }

    @ApiOperation(value = "屏蔽IP",notes = "屏蔽攻击者的IP")
    @RequestMapping(value = "/block_ip/{ip}")
    public Result BlockIP(@PathVariable String ip){
        try {
            scafetyService.BlockIP(ip);
            return new Result(true,MessageConstant.BLOCK_IP_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.BLOCK_IP_FAIL);
        }
    }

    @ApiOperation(value = "放行端口",notes = "放行指定端口")
    @RequestMapping(value = "/allow_port/{port}")
    public Result AllowPort(@PathVariable String port){
        try {
            scafetyService.AllowPort(port);
            return new Result(true,MessageConstant.ALLOW_PORT_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ALLOW_PORT_FAIL);
        }
    }

    @ApiOperation(value = "关闭端口或解除IP封锁",notes = "关闭指定项的端口或解除指定项的IP封锁")
    @RequestMapping(value = "/delete/{havior}",method = RequestMethod.POST)
    public Result Delete(@PathVariable String havior){
        try {
            scafetyService.Delete(havior);
            return new Result(true,MessageConstant.DELETE_ITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_ITEM_FAIL);
        }
    }
}
