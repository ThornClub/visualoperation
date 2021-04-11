package cc.sgee.visualoperation.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: Thorn
 * @Date: 2020/12/3 09:25
 * @Blog: https://www.sgee.cc
 * @Description: Shell执行工具类
 */
public class ExecuteShell {
    public static String GetResult(String command){
        StringBuilder buffer = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec("bash "+ command);
            p.waitFor();
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bf.readLine()) != null){
                buffer.append(line);
            }
            bf.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static String Shell(String command){
        StringBuilder buffer = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bf.readLine()) != null){
                buffer.append(line);
            }
            bf.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String Shell(String[] command){
        StringBuilder buffer = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bf.readLine()) != null){
                buffer.append(line);
            }
            bf.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
