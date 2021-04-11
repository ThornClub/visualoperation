package cc.sgee.visualoperation.common.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author: Thorn
 * @Date: 2021/3/7 02:59
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Data
@Component
public class SystemInfo {
    private double cpu;
    private double load;
    private double memory;
    private double disk;
}
