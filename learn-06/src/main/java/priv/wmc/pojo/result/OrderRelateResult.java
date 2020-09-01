package priv.wmc.pojo.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Wang Mincong
 * @date 2020-07-23 09:59:42
 */
@Getter
@Setter
public class OrderRelateResult extends OrderResult {

    /** 所属用户 */
    private UserResult user;

}
