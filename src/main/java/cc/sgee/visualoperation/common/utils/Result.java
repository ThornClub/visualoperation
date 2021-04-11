package cc.sgee.visualoperation.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: Thorn
 * @Date: 2020/11/27 14:24
 * @Blog: https://www.sgee.cc
 * @Description: 封装返回结果
 */
@ApiModel(description = "返回对象")
public class Result implements Serializable {
    @ApiModelProperty(value = "执行结果",required = true)
    private boolean flag;//执行结果，true为执行成功 false为执行失败
    @ApiModelProperty(value = "返回信息",required = true)
    private String message;//返回结果信息
    @ApiModelProperty(value = "返回数据",required = true)
    private Object data;//返回数据
    public Result(boolean flag){
        this.flag = flag;
    }
    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}