package jp.ac.uaizu.azumaza.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.ac.uaizu.azumaza.dto.Strage;

public class StrageDAO extends DataBase<Strage> {

    public List<Strage> findByUserId(int userId) throws Exception {
        final String sql = "SELECT * FROM strage WHERE (user_id = ?)";

        final Map<Integer, Object> param = new HashMap<>();
        param.put(1, userId);

        return executeQuery(Strage.class, sql, param);
    }

    public Optional<Strage> findById(int strageId) throws Exception {
        final String sql = "SELECT * FROM strage WHERE (id = ?)";

        final Map<Integer, Object> param = new HashMap<>();
        param.put(1, strageId);

        return executeQueryFirst(Strage.class, sql, param);
    }

    public Optional<Strage> deleteStrage(int userId, int strageId) throws Exception {
        final String findSql = "SELECT * FROM strage WHERE (id = ?) AND (user_id = ?)";
        final String deleteSql = "DELETE FROM strage WHERE (id = ?) AND (user_id = ?)";

        final Map<Integer, Object> param = new HashMap<>();
        param.put(1, strageId);
        param.put(2, userId);

        final Optional<Strage> strage = executeQueryFirst(Strage.class, findSql, param);
        if (strage.isPresent()) {
            executeUpdate(deleteSql, param);
        }
        return strage;
    }
}
