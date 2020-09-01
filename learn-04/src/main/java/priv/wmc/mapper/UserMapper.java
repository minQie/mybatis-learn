package priv.wmc.mapper;

import java.util.Map;
import priv.wmc.pojo.result.UserResult;

/**
 * User DML 接口定义
 *
 * @author Wang Mincong
 * @date 2020-07-20 21:15:44
 */
public interface UserMapper {

    UserResult findById(Long id);

    /** 通过构造方法的形式封装结果集 */
    UserResult findById2(Long id);

    /** 因为resultType="_int"爆红，不用理会 */
    Integer selectInteger();

//    @MapKey("id")
    Map<String, Object> findMapById(Long id);

}
