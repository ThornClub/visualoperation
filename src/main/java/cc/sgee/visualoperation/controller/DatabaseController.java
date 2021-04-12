package cc.sgee.visualoperation.controller;

import cc.sgee.visualoperation.common.constant.MessageConstant;
import cc.sgee.visualoperation.common.utils.Result;
import cc.sgee.visualoperation.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    //获取数据库基本信息
    @ApiOperation(value = "基本信息",notes = "获取数据库的基本信息")
    @RequestMapping(value = "/getinfo",method = RequestMethod.POST)
    public Result getInfo(){
        try {
            return new Result(true,"",databaseService.getInfo());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_MYSQL_INFO_FAIL);
        }
    }
    //操作数据库
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
}
