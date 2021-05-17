package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.WebSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: visualoperation
 * @description: 网站页面Controller
 * @author: Thorn
 * @create: 2021-05-07 21:23
 **/
@Api(tags = "网站操作")
@RestController
@RequestMapping("/website")
public class WebSiteController {
    @Autowired
    private WebSiteService webSiteService;

    //获取网站基本信息
    @ApiOperation(value = "基本信息",notes = "获取网站的基本信息")
    @RequestMapping(value = "/getinfo",method = RequestMethod.POST)
    public Result getInfo(){
        try {
            return new Result(true,"",webSiteService.getInfo());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_NGINX_INFO_FAIL);
        }
    }
    //操作Web服务器
    @ApiOperation(value = "开关Web服务器",notes = "开启、关闭、重启Web服务器")
    @RequestMapping(value = "/operate/{operate}",method = RequestMethod.POST)
    public Result operate(@PathVariable String operate){
        try {
            webSiteService.operate(operate);
            return new Result(true,operate+MessageConstant.OPERATE_NGINX_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.OPERATE_NGINX_FAIL);
        }
    }

    @ApiOperation(value = "添加网站",notes = "添加网站业务")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody Map<String,String> web){
        try {
            webSiteService.add(web.get("domain"),web.get("port"));
            return new Result(true,MessageConstant.ADD_WEBSITE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_WEBSITE_FAIL);
        }
    }

    @ApiOperation(value = "获取网站信息",notes = "获取所有网站信息")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Result select(){
        try {
            return new Result(true,"",webSiteService.getWebInfo());
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_WEBINFO_FAIL);
        }
    }

    @ApiOperation(value = "删除网站",notes = "删除网站配置文件及目录")
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    public Result del(@RequestBody Map<String,String> map){
        String domain = map.get("domain");
        String root = map.get("root");
        try {
            webSiteService.del(domain,root);
            return new Result(true,MessageConstant.DEL_WEBSITE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DEL_WEBSITE_FAIL);
        }
    }
}
