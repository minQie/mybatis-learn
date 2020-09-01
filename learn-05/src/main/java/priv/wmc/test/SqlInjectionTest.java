package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import priv.wmc.mapper.UserMapper;

/**
 * sql注入讲解样例
 *
 * -- sql定义：Mapper中定义的包含动态参数的sql
 * -- SELECT * FROM `user` WHERE username LIKE "%"--{}"%";
 *
 * -- sql注入实例：实际传递的参数：大明'"%";UPDATE `user` SET username = '明' WHERE id = 1;--
 * -- SELECT * FROM `user` WHERE username LIKE "%"'大明'"%";UPDATE user SET username = '明' WHERE id = 1;--'"%";
 *
 * -- 经过预编译处理后的sql
 * -- SELECT * FROM `user` WHERE username LIKE "%"'大明''"%";UPDATE `user` SET username = ''明明'' WHERE id = 1;--'"%"
 *
 * -- 预编译防止sql注入的核心原理：参考上面的示例，让sql发生本质上变味儿的是参数中，大明后边的单引号，这个单引号表达了自己身为特殊符号的含义
 * -- 而预处理就是将这些想表达特殊含义的符号，无法表达含义，一视同仁的将其作为字符串处理了
 *
 * -- 实际是由PreparedStatement的实现类（动态代理生成）处理的，处理方式就是将想表语法的单引号全部变为字符串内容的一对单引号
 * -- 验证：假如user表中，有一个username为 大明'' 的用户，通过下面的语句是可以查出来的
 * -- SELECT * FROM `user` WHERE username Like "%"'大明'''"%"
 *
 * -- 经过测试：使用转译符号修饰单引号字符，也能够防止字符表达特殊含义，起到防止sql注入的目的
 *
 * @author Wang Mincong
 * @date 2020-07-31 10:02:01
 */
public class SqlInjectionTest {

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

            userMapper.listByUsernameLike("大明'\"%\";UPDATE `user` SET username = '明' WHERE id = 1;--");
        }
    }
}
