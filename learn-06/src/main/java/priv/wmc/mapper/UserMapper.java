package priv.wmc.mapper;

import priv.wmc.pojo.result.UserRelateResult;

/**
 * User DML 接口定义
 *
 * @author Wang Mincong
 * @date 2020-07-22 19:35:43
 */
public interface UserMapper {

    UserRelateResult findById(Long userId);

}
