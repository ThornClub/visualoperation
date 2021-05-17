package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/4/12 08:24
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Api(tags = "数据库操作")
@RestController
@RequestMapping("/database")
public class DatabaseController {
    @Autowired
    private DatabaseService databaseService;

    /**
     * 获取数据库基本信息
     */
    @ApiOperation(value = "状态信息",notes = "获取数据库的基本信息")
    @RequestMapping(value = "/getinfo",method = RequestMethod.POST)
    public Result getInfo(){
        try {
            return new Result(true,"",databaseService.getInfo());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_MYSQL_INFO_FAIL);
        }
    }

    /**
     * 操作数据库
     */
    @ApiOperation(value = "开关数据库",notes = "开启、关闭、重启数据库")
    @RequestMapping(value = "/operate/{operate}",method = RequestMethod.POST)
    public Result operate(@PathVariable String operate){
        try {
            databaseService.operate(operate);
            return new Result(true,operate+MessageConstant.OPERATE_MYSQL_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.OPERATE_MYSQL_FAIL);
        }
    }

    /**
     * 获取所有表名
     */
    @ApiOperation(value = "获取所有表",notes = "获取所有用户表")
    @RequestMapping(value = "/alldatabase",method = RequestMethod.POST)
    public Result allDatabase(){
        return new Result(true,"",databaseService.getAllDatabase());
    }

    /**
     * 删除数据库操作
     */
    @ApiOperation(value = "删除数据库",notes = "删除指定的数据库")
    @RequestMapping(value = "/delete/{name}",method = RequestMethod.DELETE)
    public Result delDatabase(@PathVariable String name){
        try {
            databaseService.delDatabase(name);
            return new Result(true,MessageConstant.DEL_DATABASE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DEL_DATABASE_FAIL);
        }
    }

    /**
     * 备份数据库
     * @param name
     */
    @ApiOperation(value = "备份数据库",notes = "备份数据库操作")
    @RequestMapping(value = "/backup/{name}",method = RequestMethod.GET)
    public Result backupDatabase(@PathVariable String name){
        try {
            databaseService.backupDatabase(name);
            return new Result(true,MessageConstant.BACK_DATABASE_SUCCESS+name+".sql");
        } catch (Exception e) {
            return new Result(false,MessageConstant.BACK_DATABASE_FAIL);
        }
    }

    /**
     * 返回数据库支持的字符集
     */
    @ApiOperation(value = "返回数据库支持的字符集",notes = "返回数据库支持的字符集")
    @RequestMapping(value = "/getcharacter",method = RequestMethod.GET)
    public Result dbCharacter(){
        try {
            return new Result(true,"",databaseService.getDbCharacter());
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_DBCHARACTER_FAIL);
        }
    }

    /**
     * 返回数据库字符集的排序规则
     */
    @ApiOperation(value = "返回数据库字符集的排序规则",notes = "返回数据库字符集的排序规则")
    @RequestMapping(value = "/getsortrules/{character}",method = RequestMethod.GET)
    public Result dbSortRules(@PathVariable String character){
        try {
            return new Result(true,"",databaseService.getDbSortRules(character));
        } catch (Exception e) {
            return new Result(false,MessageConstant.GET_DBSORTRULES_FAIL);
        }
    }

    @ApiOperation(value = "添加数据库",notes = "添加数据库操作")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result addDataBase(@RequestBody Map<String,String> map){
        String databaseName = map.get("name");
        String character = map.get("character");
        String sortrules = map.get("sortrules");
        try {
            databaseService.addDataBase(databaseName,character,sortrules);
            return new Result(true,MessageConstant.ADD_DATABASE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_DATABASE_FAIL);
        }
    }

    @ApiOperation(value = "更改root密码",notes = "更改数据库root密码")
    @RequestMapping(value = "/changepd",method = RequestMethod.POST)
    public Result changePd(@RequestBody Map<String,String> map){
        String pd = map.get("password");
        try {
            databaseService.changePd(pd);
            return new Result(true,MessageConstant.CHANGE_PD_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.CHANGE_PD_FAIL);
        }
    }
}
