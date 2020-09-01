package priv.wmc.pojo.result;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Wang Mincong
 * @date 2020-07-23 09:59:42
 */
@Getter
@Setter
public class OrderResult {

    /** 主键id */
    private Long id;

    /** 编号 */
    private String code;

    /** 金额 */
    private Double money;

    /** 创建时间 */
    private LocalDateTime gmtCreate;

    /** 更新时间 */
    private LocalDateTime gmtModified;

}
