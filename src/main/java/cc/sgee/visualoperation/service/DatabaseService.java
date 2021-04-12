package cc.sgee.visualoperation.service;

import java.util.List;
import java.util.Map;

/**
 * @author: Thorn
 * @Date: 2021/4/12 08:25
 * @Blog: https://www.sgee.cc
 * @Description:
 */
public interface DatabaseService {
    List<Map<String,String>> getInfo();
    void operate(String operate);
}
