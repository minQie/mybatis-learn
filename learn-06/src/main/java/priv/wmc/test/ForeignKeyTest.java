package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.mapper.OrderMapper;
import priv.wmc.mapper.OrderMapper2;
import priv.wmc.mapper.UserMapper;
import priv.wmc.mapper.UserMapper2;
import priv.wmc.pojo.result.OrderRelateResult;
import priv.wmc.pojo.result.UserRelateResult;

/**
 * <p>1. 创建新的订单实体以及对应的结果集实体, OrderMapper.java, OrderMapper.xml
 * <p>2. 演示关联查询 - 关联查询结果集封装的相关标签使用
 *
 * @author Wang Mincong
 * @date 2020-07-22 19:34:41
 */
public class ForeignKeyTest {

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
            UserMapper2 userMapper2 = sqlSession.getMapper(UserMapper2.class);
            OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
            OrderMapper2 orderMapper2 = sqlSession.getMapper(OrderMapper2.class);

            OrderRelateResult byId = orderMapper2.listByUserId(1L);

            // 查询用户信息(附带用户的所有订单信息) - 分段sql
            UserRelateResult userRelateResult1 = userMapper.findById(1L);

            // 查询用户信息(附带用户的所有订单信息) - 嵌套查询（join）
            UserRelateResult userRelateResult2 = userMapper2.findById(1L);

            // 查询用户的订单(附带订单关联的用户信息)
            List<OrderRelateResult> orderList = orderMapper.listByUserId(1L);

            System.out.println("123");
        }
    }

}
