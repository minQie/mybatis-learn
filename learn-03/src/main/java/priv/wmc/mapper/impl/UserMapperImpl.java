package priv.wmc.mapper.impl;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.entity.User;
import priv.wmc.pojo.result.UserResult;

/**
 * User的DML实现
 *
 * @author Wang Mincong
 * @date 2020-07-20 20:53:03
 */
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {

    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public UserResult findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("test.findById", 1L);
        }
    }

    @Override
    public List<UserResult> listByUsernameLike(String username) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("test.listByUsernameLike", username);
        }
    }

    @Override
    public Integer save(User user) {
        int id;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            id = session.insert("test.save", user);
            session.commit();
        }
        return id;
    }

    @Override
    public Integer update(User user) {
        int result;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            result = session.update("test.update", user);
            session.commit();
        }
        return result;
    }

    @Override
    public Integer deleteById(Long id) {
        int result;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            result = session.delete("test.deleteById", id);
            session.commit();
        }
        return result;
    }

    @Override
    public Map<String, Object> selectNumberTest() {
        Map<String, Object> result;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            result = session.selectOne("test.selectNumberTest");
            session.commit();
        }
        return result;
    }

}
