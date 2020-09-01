package priv.wmc.mapper;

import java.util.List;
import java.util.Map;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * User的DML接口定义
 *
 * @author Wang Mincong
 * @date 2020-07-20 21:15:44
 */
public interface UserMapper {

    /**
     * 通过id，查询用户
     */
    UserResult findById(Long id);

    /**
     * 通过用户名模糊查询用户列表
     */
    List<UserResult> listByUsernameLike(String username);

    /**
     * 新增用户
     */
    Integer save(User user);

    /**
     * 修改用户
     */
    Integer update(User user);

    /**
     * 删除用户
     */
    Integer deleteById(Long id);

    /** 查询返回浮点数 */
    Map<String, Object> selectNumberTest();
}
