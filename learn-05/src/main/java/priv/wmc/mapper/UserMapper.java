package priv.wmc.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Update;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.param.UserQueryVo;
import priv.wmc.pojo.result.OrderResult;
import priv.wmc.pojo.result.UserResult;

/**
 * User的DML接口定义
 *
 * @author Wang Mincong
 * @date 2020-07-20 21:15:44
 */
public interface UserMapper {

    /**
     * 这里有一点，Mapper.java 中定义的查询接口方法的返回值和Mapper.xml定义 select 标签的 resultMap 返回值类型对不上也不会报错
     *
     * 因为 mybatis 走的动态代理，只按照 Mapper.xml 中 resultMap 的 type 属性来决定返回值的
     * 所以，假如 Mapper.java 的接口方法定义的返回值类型错了，赋值是通过反射赋值的，赋值成功没有问题（java的泛型，呵呵...），
     * 但是通过ArrayList.get方法取值时的类型转换，就等着ClassCastException咯
     */
    List<OrderResult> listByIdArray(Long[] idArray);

    List<UserResult> listByIdList(List<Long> idList);

    List<UserResult> listByQueryVo(UserQueryVo userQueryVo);

    List<UserResult> listByUsernameLikeAndGender(String username, String gender);

    List<UserResult> listByUser1UsernameAndUser2Gender(User user1, User user2);

    List<UserResult> listByMap1UsernameAndMap2Gender(Map<String, String> map1, Map<String, String> map2);

    @Update({"<script>",
        "update `user`",
        "  <set>",
        "    <if test=\"username != null and username != ''\">username=#{username},</if>",
        "    <if test=\"gender != null\">gender=#{gender},</if>",
        "    <if test=\"birthday != null\">birthday=#{birthday},</if>",
        "    <if test=\"address != null and address != ''\">address=#{address},</if>",
        "    <if test=\"gmtModified != null\">gmt_modified=#{gmtModified},</if>",
        "  </set>",
        "where id=#{id}",
        "</script>"})
    void updateIfNecessary(User user);

    /**
     * 演示sql注入：
     * 1、注意你不能既定义注解来表达实现，又在Mapper.xml定义对应的实现，这样会导致执行时的异常（Error building SqlSession）
     * 2、下面的注解表达和对应的Mapper.xml中的表达，能起到相同的防止sql注入的作用
     */
//    @ResultMap("userResult")
//    @Select("SELECT * FROM `user` WHERE username LIKE \"%\"#{param1}\"%\"")
    List<UserResult> listByUsernameLike(String username);

}
