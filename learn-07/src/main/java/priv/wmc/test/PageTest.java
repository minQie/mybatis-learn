package priv.wmc.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * <p>官方文档：https://pagehelper.github.io/docs/howtouse</p>
 *
 * <h4>分页参数形式</h4>
 *
 * <h2>offset limit</h2>
 * <p>同mysql的limit关键字的语法，offset参数代表开始返回数据的下标，limit参数代表要查多少条
 * <p>offset
 *     初始值为0
 *     为负数时：语法有误
 * <p>limit
 *     为0时：https://dev.mysql.com/doc/refman/8.0/en/limit-optimization.html
 *     为负数时：语法有误
 *
 * <h2>pageNum pageSize</h2>
 * <p>封装过的语法，更贴近真实的分页业务场景，pageNum参数代表页码，pageSize参数代表每页多少条
 * <p>pageNum
 *     初始值为1
 *     为0时：查不出数据
 *     为负数时：查不出数据
 * <p>pageSize
 *     为0时、为负数时：查不出数据（见 AbstractHelperDialect.beforePage 方法，如果不大于0，就不进行分页查询 - 见ExecutorUtil.pageQuery）
 * @author Wang Mincong
 * @date 2020-08-08 15:00:52
 */
@Log4j2
public class PageTest {

    private final SqlSessionFactory sqlSessionFactory;

    @SneakyThrows
    public PageTest() {
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() throws IOException {
//        rowBoundPageDemo();
//        pageHelperDemo2();
//        pageHelperDemo3();
//        pageHelperDemo4();
//        pageHelperDemo5();
        pageHelperDemo6();
    }

    /**
     * mybatis 原生支持的分页查询
     *
     * 这里边存在的问题：如果不引入 PageHelper 的 PageInterceptor，最终查询的结果是分页的，
     * 但是日志打印的sql没有显示分页相关的语句（limit），Mybatis Sql Log 也是
     */
    public void rowBoundPageDemo() throws IOException {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            List<UserResult> userResultList = sqlSession.selectList("priv.wmc.mapper.UserMapper.pageByBasic", null, new RowBounds(0, 2));
            for (UserResult userResult : userResultList) {
                log.info(userResult.toString());
            }
        }
    }

    /**
     * 调用PageHelper的静态方法，代表进行接下来的查询需要分页
     */
    public void pageHelperDemo2() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // PageHelper.startPage 被 SonarLint 怼了
            PageMethod.startPage(1, 2);
            List<UserResult> userResultList = userMapper.pageByBasic();

            for (UserResult userResult : userResultList) {
                log.info(userResult.toString());
            }
        }
    }

    /**
     * 调用PageHelper的静态方法，代表进行接下来的查询需要分页
     */
    public void pageHelperDemo3() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            PageMethod.offsetPage(0, 2);
            List<UserResult> userResultList = userMapper.pageByBasic();

            for (UserResult userResult : userResultList) {
                log.info(userResult.toString());
            }
        }
    }

    /**
     * Mapper.java 接口方法添加分页参数
     */
    public void pageHelperDemo4() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 你会发现PageHelper提供的分页方式，传分页参数的参数类型都是原始数据类型
            // 像这里的写法是可以自定义，如果你改为Integer，并且实际传null，那么将PageHelper不会进行分页
            List<UserResult> userResultList = userMapper.pageByPageNumSize(1, 2);

            for (UserResult userResult : userResultList) {
                log.info(userResult.toString());
            }
        }
    }

    /**
     * 在参数实体中添加 pageNum pageSize
     */
    public void pageHelperDemo5() {
        // 不做Demo示例了
    }

    /**
     * jdk8 lambda
     */
    public void pageHelperDemo6() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

            // 分页查询1
            Page<User> page = PageMethod.startPage(1, 10).doSelectPage(userMapper::pageByBasic);
            // 分页查询2
            PageInfo<UserResult> pageInfo = PageMethod.startPage(1, 10).doSelectPageInfo(userMapper::pageByBasic);

            // count查询
            long total = PageMethod.count(userMapper::pageByBasic);
        }
    }

}
