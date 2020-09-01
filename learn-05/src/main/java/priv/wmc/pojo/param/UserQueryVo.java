package priv.wmc.pojo.param;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import priv.wmc.pojo.entity.User;

/**
 * @author Wang Mincong
 * @date 2020-07-22 16:39:05
 */
@Getter
@Setter
public class UserQueryVo {

    /** 用户的相关属性作为查询条件 */
    private User user;

    /** 列表查询条件 - List */
    private List<Long> idList;

    /** 列表查询条件 - Array */
    private Long[] idArray;

}
