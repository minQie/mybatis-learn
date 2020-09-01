package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.entity.User;

/**
 * @author Wang Mincong
 * @date 2020-08-08 16:47:50
 */
public class UpdatePluginTest {

    @Test
    public void test() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            User user = userMapper.findById(1L);

            // 更新时间由 priv.wmc.interceptor.UpdateSetGmtModifiedInterceptor 设置值
            user.setUsername("牢头浴霸");

            userMapper.updateIfNotNull(user);
        }
    }

}
