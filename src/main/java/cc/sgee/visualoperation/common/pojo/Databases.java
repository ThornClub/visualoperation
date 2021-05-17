package cc.sgee.visualoperation.common.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @program: visualoperation
 * @description: 数据库信息实体类
 * @author: Thorn
 * @create: 2021-05-09 09:27
 **/
@Component
@Data
public class Databases {
    private String dataBaseName;
    private int dataBaseTableNum;
}
