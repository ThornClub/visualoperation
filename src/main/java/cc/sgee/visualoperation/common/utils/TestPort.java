package cc.sgee.visualoperation.common.utils;

/**
 * @author: Thorn
 * @Date: 2021/3/7 17:54
 * @Blog: https://www.sgee.cc
 * @Description:
 */
public class TestPort {
    public static boolean test(String port){
        String is = ExecuteShell.GetResult("TestPort.sh " + port);
        if (is.equals("0")){
            return false;
        }
        else if (is.equals("1")){
            return true;
        }
        return false;
    }
}
