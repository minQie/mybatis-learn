package priv.wmc.mapper;

import org.apache.ibatis.annotations.Select;
import priv.wmc.pojo.result.UserResult;

/**
 * @author Wang Mincong
 * @date 2020-08-06 21:00:44
 */
public interface TestMapper {

    @Select("SELECT * FROM user WHERE id = #{arg0}")
    UserResult findById(Long id);

}
