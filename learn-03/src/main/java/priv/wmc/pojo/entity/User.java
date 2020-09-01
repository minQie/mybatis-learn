package priv.wmc.pojo.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import priv.wmc.basic.enums.GenderEnum;

/**
 * 用户实体
 *
 * @author Wang Mincong
 * @date 2020-07-20 11:43:09
 */
@Getter
@Setter
@Builder
public class User {

    /** 主键id */
    private Long id;

    /** 姓名 */
    private String username;

    /** 性别 */
    private GenderEnum gender;

    /** 生日 */
    private LocalDateTime birthday;

    /** 地址 */
    private String address;

    /** 创建时间 */
    private LocalDateTime gmtCreate;

    /** 更新时间 */
    private LocalDateTime gmtModified;

}
