package jp.ac.uaizu.azumaza.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.ac.uaizu.azumaza.dto.User;

public class UserDAO extends DataBase<User> {

    public List<User> findAll() throws Exception {
        final String sql = "SELECT * FROM user";
        return executeQuery(User.class, sql, null);
    }

    public Optional<User> findById(Integer id) throws Exception {
        final String sql = "SELECT * FROM user WHERE (id = ?)";

        final Map<Integer, Object> param = new HashMap<>();
        param.put(1, id);

        return executeQueryFirst(User.class, sql, param);
    }

    public Optional<User> findByName(String name) throws Exception {
        final String sql = "SELECT * FROM user WHERE (name = ?)";

        final Map<Integer, Object> param = new HashMap<>();
        param.put(1, name);

        return executeQueryFirst(User.class, sql, param);
    }
}
