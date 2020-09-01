package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.mapper.UserMapper;

/**
 * mapper.xml 的 resultType 测试
 *
 * @author Wang Mincong
 * @date 2020-07-28 09:58:18
 */
public class ResultTypeTest {

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
            // 测试1 返回值类型 基本类型（resultType一定要指定且必须正确）
//        Integer integer = userMapper.selectInteger();

            // 测试2 返回值类型 Map
            //   有@MapKey("id") 返回值 {"id": "1", "username": "小明", ...}（HashMap）
            // 没有@MapKey("id") 返回值 {"1": {"id": "1", "username": "小明", ...}（HashMap）}
            Map<String, Object> map = userMapper.findMapById(1L);
            System.out.println(map);
        }
    }

}
