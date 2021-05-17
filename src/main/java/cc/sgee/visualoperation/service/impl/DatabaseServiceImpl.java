package cc.sgee.visualoperation.service.impl;

import cc.sgee.visualoperation.common.pojo.Databases;
import cc.sgee.visualoperation.common.utils.ExecuteShell;
import cc.sgee.visualoperation.common.utils.YmlUtil;
import cc.sgee.visualoperation.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author: Thorn
 * @Date: 2021/4/12 08:26
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@RefreshScope
@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Qualifier("configDataContextRefresher")
    @Autowired
    private ContextRefresher contextRefresher;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 获取数据库状态信息及版本号
     * @return
     */
    @Override
    public List<Map<String, String>> getInfo() {
        List<Map<String,String>> info = new ArrayList<>();
        Map<String,String> mapInfo = new HashMap<>();
        String result = ExecuteShell.GetResult("./sh/GetMysqlInfo.sh");
        String mysqlStatus;
        if ("0".equals(result.split(" ")[0]))
        {
            mysqlStatus = "Running";
        }
        else
        {
            mysqlStatus = "Stopped";
        }
        mapInfo.put("mysqlStatus",mysqlStatus);
        mapInfo.put("mysqlVersion",result.split(" ")[1]);
        info.add(mapInfo);
        return info;
    }

    /**
     * 操作数据库状态
     * @param operate
     */
    @Override
    public void operate(String operate) {
        switch (operate){
            case "Start":
                ExecuteShell.Shell("service mysqld start");
                break;
            case "Stop":
                ExecuteShell.Shell("service mysqld stop");
                break;
            case "Restart":
                ExecuteShell.Shell("service mysqld restart");
                break;
            default:
        }
    }

    /**
     * 获取数据库名+表的数量
     * @return
     */
    @Override
    public List<Databases> getAllDatabase() {
        List<Databases> allDatabaseInfo = new ArrayList<>();
        List<String> systemDataBase = Arrays.asList("information_schema","mysql","performance_schema","sys");
        //获取connect对象
        try {
            Connection con = jdbcTemplate.getDataSource().getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            //检索数据库名称列表
            ResultSet tables = metaData.getCatalogs();
            while (tables.next()){
                int tableNum = 0;
                String dataBaseName = tables.getString("TABLE_CAT");
                if (!systemDataBase.contains(dataBaseName)){
                    ResultSet set = metaData.getTables(dataBaseName, null, "%", null);
                    while (set.next()){
                        tableNum++;
                    }
                    Databases databases = new Databases();
                    databases.setDataBaseName(dataBaseName);
                    databases.setDataBaseTableNum(tableNum);
                    allDatabaseInfo.add(databases);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDatabaseInfo;
    }

    /**
     * 删除数据库操作
     * @param name
     */
    @Override
    public void delDatabase(String name) {
        try {
            jdbcTemplate.execute("drop database " + name);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 备份数据库操作
     * @param name
     */
    @Override
    public void backupDatabase(String name) {
        try {
            ExecuteShell.Shell("./sh/Db_backup.sh "+username+" "+password+" "+name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getDbCharacter() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select character_set_name from information_schema.character_sets;");
        List<String> Arraycharacter = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = (String) list.get(i).get("character_set_name");
            Arraycharacter.add(name);
        }
        return Arraycharacter;
    }

    @Override
    public List<String> getDbSortRules(String character) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select collation_name from information_schema.collations where character_set_name='" + character +"'");
        List<String> Arraysortrules = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = (String) list.get(i).get("collation_name");
            Arraysortrules.add(name);
        }
        return Arraysortrules;
    }

    @Override
    public void addDataBase(String databaseName, String character, String sortrules) {
        try {
            jdbcTemplate.execute("CREATE DATABASE IF NOT EXISTS "+databaseName+" DEFAULT CHARACTER SET "+character+" DEFAULT COLLATE "+sortrules+";");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePd(String pd) {
        try {
            ExecuteShell.Shell("./sh/ResetMysqlPd.sh "+password+" "+pd);
            File yml = new File("application.yml");
            YmlUtil.setYmlFile(yml);
            YmlUtil.saveOrUpdateByKey("spring.datasource.password",pd);
            new Thread(() -> contextRefresher.refresh()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
