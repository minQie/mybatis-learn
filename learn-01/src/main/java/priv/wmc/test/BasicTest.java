package priv.wmc.test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import priv.wmc.basic.enums.GenderEnum;
import priv.wmc.pojo.entity.User;

import priv.wmc.pojo.result.UserResult;
import priv.wmc.util.TimeUtils;

/**
 * <p>1、仅通过核心配置文件sqlMapConfig.xml、以及XxxMapper.xml，演示最基础的CURD
 * <p>2、为了演示规范的业务写法：引入枚举、查询的结果集实体不使用数据表实体（创建相应的结果集实体）
 * <p>3、默认的 openSession() 方法没有参数，它会创建具备如下特性的 SqlSession：
 * <ul>
 *     <li>事务作用域将会开启（也就是不自动提交）
 *     <li>将由当前环境配置的 DataSource 实例中获取 Connection 对象
 *     <li>事务隔离级别将会使用驱动或数据源的默认设置
 *     <li>预处理语句不会被复用，也不会批量处理更新
 *
 * @author Wang Mincong
 * @date 2020-07-20 11:32:15
 */
public class BasicTest {

    @Test
    public void demo() throws IOException {
        // 一、加载sqlMapConfig.xml核心配置文件
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        // 二、创建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 三、从SqlSessionFactory获取一个SqlSession
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            User user = User.builder()
                .username("小明")
                .gender(GenderEnum.MALE)
                .birthday(LocalDateTime.of(1997, 10, 5, 0, 0))
                .build();

            // 增
            this.save(session, user);
            // 查
            UserResult userResult = this.findById(session, user.getId());
            List<UserResult> userList = this.listByUsernameLike(session, "小明");
            // 改
            user.setUsername("大明");
            user.setGmtModified(TimeUtils.now());
            this.update(session, user);
            // 删
            this.deleteById(session, user.getId());
        }
    }

    /**
     * 查
     */
    public UserResult findById(SqlSession session, Long id) {
        return session.selectOne("test.findById", id);
    }
    public List<UserResult> listByUsernameLike(SqlSession session, String username) {
        return session.selectList("test.listByUsernameLike", username);
    }

    /**
     * 增
     *
     * 关于事务，如果获取的 SqlSession 的 autoCommit 属性是 false，在进行相关的数据增删改操作时
     * 不手动调用提交事务方法 sqlSession.commit方法，数据表中的数据是不会有变化的
     *
     * 先提：
     * sqlSessionFactory.openSession(true)，不传参的 openSession 方法，默认是 false
     * sqlSession.commit(true)，不传参的 commit 方法，默认是 false
     *
     * 分析：
     * SqlSessionFactory.openSession → DefaultSqlSessionFactory.openSession → 通过该方法获取一个 SqlSession 的同时，会开启一个事务
     * SqlSession.commit → DefaultSqlSession.commit → BaseExecutor.commit → 根据参数决定是否提交事务
     * autoCommit - false
     *     false：(!autoCommit（false） && dirty（true）) || force（false） → true
     *     true： (!autoCommit（false） && dirty（true）) || force（true） → true
     * autoCommit - true
     *     false：(!autoCommit（true） && dirty（true）) || force（false） → false（开启了自动提交，如果手动提交 force 为 false，就不会提交事务）
     *     true： (!autoCommit（true） && dirty（true）) || force（true） → true
     *
     * SqlSession.close → DefaultSqlSession.close → 会根据 (!autoCommit && dirty) || force 的值来决定是提交事务还是回滚，
     *     这个表达式中就包含两个元素：开启SqlSession时设置的 autoCommit、sqlSession 设置的 force
     */
    public int save(SqlSession session, User user) {
        int result = session.insert("test.save", user);
        session.commit();
        return result;
    }

    /**
     * 改
     */
    public int update(SqlSession session, User user) {
        int result = session.update("test.update", user);
        session.commit();
        return result;
    }

    /**
     * 删
     */
    public int deleteById(SqlSession session, Long id) {
        int result = session.delete("test.deleteById", id);
        session.commit();
        return result;
    }

}
