package priv.wmc.pojo.result;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Wang Mincong
 * @date 2020-07-23 09:59:42
 */
@Getter
@Setter
public class UserRelateResult extends UserResult {

    /** 用户的订单 */
    private List<OrderResult> orders;

}
