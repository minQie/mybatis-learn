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
import priv.wmc.pojo.result.UserResult;

/**
 * <ul>
 * <li>1、sqlMapConfig的数据源配置，存放在一个特定的文件管理起来（引入properties文件）
 *      <p>首先读取在 properties 元素体内指定的属性。
 *      然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并覆盖之前读取过的同名属性。
 *      最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。
 *      因此，通过方法参数传递的属性具有最高优先级，resource/url 属性中指定的配置文件次之，最低优先级的则是 properties 元素中指定的属性
 *
 * <li>2、Mapper.xml不想所有的类都写上完整的类全名（定义包名的别名）
 *      <p>类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写
 *      <p>除了在Mapper.xml中为特定的类配置别名, 还能够为指定包的所有类, 按照默认的规则统一配置别名
 *      这时如果希望能够额外为一些类特殊定义别名, 可以通过加在Mapper.java的@Alias("xxx")注解来配置别名
 *
 *      <p><b>各种类型的别名见: https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases</b></p>
 *
 * <li>3、resultMap的映射有两种形式
 *     <p>1、resultMap 标签下的 id和result 标签（空参构造 + set方法）
 *     <p>2、resultMap 标签下的 constructor标签（idArg标签 + arg标签）
 *     <p>（在结果集实体类中添加指定参数的构造函数 - 注：是添加，保留无参构造，否则会影响之前的代码）
 *
 * </ul>
 * @author Wang Mincong
 * @date 2020-07-22 10:24:38
 */
public class ResultMapTest {

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

            UserResult user = userMapper.findById(1L);
            UserResult user2 = userMapper.findById2(1L);

            System.out.println(user2);
        }
    }

}
