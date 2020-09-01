package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.basic.enums.GenderEnum;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * @author Wang Mincong
 * @date 2020-07-27 16:45:35
 */
public class MultiParameterTest {

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

            // test1 multi simple
            List<UserResult> userResultList = userMapper.listByUsernameLikeAndGender("小明", "男");

            // test2 multi pojo
            User user1 = User.builder().username("小明").build();
            User user2 = User.builder().gender(GenderEnum.MALE).build();

            List<UserResult> userResultList2 = userMapper.listByUser1UsernameAndUser2Gender(user1, user2);

            // test3 multi map
            Map<String, String> map1 = Collections.singletonMap("username", "小明");
            Map<String, String> map2 = Collections.singletonMap("gender", "男");
            List<UserResult> userResult3 = userMapper.listByMap1UsernameAndMap2Gender(map1, map2);
        }
    }

}
