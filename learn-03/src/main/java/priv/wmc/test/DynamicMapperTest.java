package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.basic.enums.GenderEnum;
import priv.wmc.mapper.TestMapper;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * <p>1.在 learn-02 的基础上，不再手动实现Mapper，使用sqlSession.getMapper(XxxMapper.class)，获取动态代理的Mapper实现
 * <ul>注意:
 * <li> XxxMapper.xml的mapper标签的namespace属性值, 必须和XxxMapper.java的完整类名对应上
 * <li> select等相关语句定义方法的id, 需要和XxxMapper.java接口方法名对应上
 * </ul>
 *
 * <p>2.添加sql日志
 * <p>集成log4j2: 引入依赖, 添加log4j2配置文件, Mybatis会按照默认的排序规则选择日志框架（前提: 项目环境集成了各种日志框架）
 * <p>详见: https://mybatis.org/mybatis-3/zh/logging.html
 * <p>引入日志后，Idea的插件 Mybatis Log Plugin就生效了
 * <ul>注意：为什么到这里才引入日志，难道不是一开始就应该引入日志的么，原因是日志的原理就是通过SqlSession动态代理获取Mapper实现类时，添加上输出日志的相关操作</ul>
 *
 * @author Wang Mincong
 * @date 2020-07-22 09:59:07
 */
public class DynamicMapperTest {

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
            User user = User.builder()
                .username("小明")
                .gender(GenderEnum.MALE)
                .birthday(LocalDateTime.of(1997, 10, 5, 0, 0, 0))
                .address("上海")
                .build();

            // 增（调用完下面的方法，数据库中并看不到数据 - 未提交事务、可重复读事务级别）
            userMapper.save(user);
            sqlSession.commit();

            // 查
            UserResult userResult = userMapper.findById(user.getId());
            List<UserResult> userResultList = userMapper.listByUsernameLike("小明");

            // 改
            user.setUsername("大明");
            user.setGmtModified(LocalDateTime.now());
            userMapper.update(user);
            sqlSession.commit();

            // 删
            userMapper.deleteById(user.getId());
            sqlSession.commit();

            // 其他
            Map<String, Object> resultMap = userMapper.selectNumberTest();
            // java.lang.Double
            System.out.println(resultMap.get("num").getClass().getName());
        }


    }

}
