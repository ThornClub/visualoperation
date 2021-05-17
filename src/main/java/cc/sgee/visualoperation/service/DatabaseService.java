package cc.sgee.visualoperation.service;

import cc.sgee.visualoperation.common.pojo.Databases;

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
    List<Databases> getAllDatabase();
    void delDatabase(String name);
    void backupDatabase(String name);
    List<String> getDbCharacter();
    List<String> getDbSortRules(String character);
    void addDataBase(String databaseName, String character, String sortrules);
    void changePd(String pd);
}
