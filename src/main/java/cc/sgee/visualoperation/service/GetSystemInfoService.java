package cc.sgee.visualoperation.service;

import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2020/12/3 09:37
 * @Blog: https://www.sgee.cc
 * @Description:
 */
public interface GetSystemInfoService {
    //获取固定的信息
    List<Map> GetFixInfo();
    //返回所有实时信息
    List<Map> GetAll();
    //CPU
    String GetCpu() throws InterruptedException;
    //设置信息
    List<Map> settings();
}
