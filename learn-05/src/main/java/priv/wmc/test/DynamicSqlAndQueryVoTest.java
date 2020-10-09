package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.param.UserQueryVo;
import priv.wmc.pojo.result.UserResult;

/**
 * 复杂的查询条件测试
 * <p>1、动态sql - 演示各种标签的使用
 * <p>2、将查询条件封装到一个查询实体中传递到Mapper.xml中处理
 * <p>3、通过注解的方式描述Mapper的具体实现(在注解中书写sql语句)
 * <p>4、如果真的需要在 Java 代码中需要嵌入 SQL 语句，Mybatis针对这个也给出了解决方法（https://mybatis.org/mybatis-3/zh/statement-builders.html）
 *
 * @author Wang Mincong
 * @date 2020-07-22 16:37:49
 */
public class DynamicSqlAndQueryVoTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void initSqlSessionFactory() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            List<Long> idList = Arrays.asList(1L, 2L, 3L);
            Long[] idArray = {1L, 2L, 3L};
            // Long[] idArray = idList.toArray(new Long[0]);
            // List<Long> idList = Arrays.asList(idArray);

            // id数组查询条件
            List<UserResult> userList1 = userMapper.listByIdArray(idArray);

            // id列表查询条件
            List<UserResult> userList2 = userMapper.listByIdList(idList);

            // 放在 map 里边的id数组查询条件
            Map<String, Long[]> map = new HashMap<>(2);
            map.put("idArray", idArray);
            List<UserResult> userList3 = userMapper.listByIdArrayMap(map);

            // 放在 map 里边的id查询条件
            Map<String, Long> map2 = new HashMap<>(5);
            map2.put("id1", 1L);
            map2.put("id2", 2L);
            map2.put("id3", 3L);
            List<UserResult> userList4 = userMapper.listByIdMap(map2);

            // 集成查询条件
            UserQueryVo queryVo = new UserQueryVo();
            queryVo.setUser(User.builder().username("小明").build());
            queryVo.setIdList(idList);
            queryVo.setIdArray(idArray);

            List<UserResult> userList5 = userMapper.listByQueryVo(queryVo);

            // 更新
            User user = User.builder()
                .id(1L)
                .username("大明")
                .gmtModified(LocalDateTime.now())
                .build();
            userMapper.updateIfNecessary(user);
            sqlSession.commit();
        }
    }

}
