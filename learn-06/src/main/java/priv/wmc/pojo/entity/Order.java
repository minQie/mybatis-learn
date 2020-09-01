package priv.wmc.pojo.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 订单实体
 *
 * @author Wang Mincong
 * @date 2020-07-22 19:22:06
 */
@Getter
@Setter
public class Order {

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

    /** 所属用户 */
    private Long userId;

}
