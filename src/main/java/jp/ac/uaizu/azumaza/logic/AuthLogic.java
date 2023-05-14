package jp.ac.uaizu.azumaza.logic;

import java.util.Optional;

import jp.ac.uaizu.azumaza.dao.UserDAO;
import jp.ac.uaizu.azumaza.dto.User;

public class AuthLogic {

    final private UserDAO userDao = new UserDAO();

    public Optional<User> tryLogin(String name, String password) {

        try {
            Optional<User> user = userDao.findByName(name);
            return user.filter(u -> u.getPassword().equals(password));
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
