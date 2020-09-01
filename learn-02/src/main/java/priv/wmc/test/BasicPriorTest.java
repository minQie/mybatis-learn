package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.basic.enums.GenderEnum;
import priv.wmc.mapper.UserMapper;
import priv.wmc.mapper.impl.UserMapperImpl;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * <p>1、在 learn-01 的基础上改进获取SqlSession的方式
 * <p>2、将SqlSession专门操作sql的相关方法封装到专门的接口、类中
 *
 * @author Wang Mincong
 * @date 2020-07-20 11:32:15
 */
public class BasicPriorTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void initSqlSessionFactory() throws IOException {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void demo() {
        UserMapper userMapper = new UserMapperImpl(sqlSessionFactory);

        User user = User.builder()
            .username("小明")
            .gender(GenderEnum.MALE)
            .birthday(LocalDateTime.of(1997, 10, 5, 0, 0, 0))
            .address("上海")
            .build();

        // 增
        userMapper.save(user);
//        userMapper.save2(user);

        // 查
        UserResult userResult = userMapper.findById(user.getId());
        List<UserResult> userResultList = userMapper.listByUsernameLike("小明");

        // 改
        user.setUsername("大明");
        user.setGmtModified(LocalDateTime.now());
        userMapper.update(user);

        // 删
        userMapper.deleteById(user.getId());
    }

}
