package cc.sgee.visualoperation.common.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

/**
 * @author: Thorn
 * @Date: 2021/3/4 23:06
 * @Blog: https://www.sgee.cc
 * @Description:
 */
@Getter
@Setter
@Repository
public class Crontab {
    private String desc;
    private String crontab;
}
