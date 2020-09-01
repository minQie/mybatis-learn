package priv.wmc.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * @author Wang Mincong
 * @date 2020-08-08 14:50:55
 */
public interface UserMapper {

    List<UserResult> pageByBasic();

    List<UserResult> pageByPageNumSize(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    User findById(Long id);
    void updateIfNotNull(User user);

}
