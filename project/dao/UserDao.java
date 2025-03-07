package vn.edu.hcmuaf.fit.demo.dao;

import vn.edu.hcmuaf.fit.demo.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.demo.dao.model.User;
import org.jdbi.v3.core.Jdbi;

import java.sql.Timestamp;

public class UserDao {
    private Jdbi jdbi = JdbiConnect.get();


    public User findUser(String username) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, username)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }

    public User findUserByEmail(String email) {
        return findUser(email);
    }


    public int getUserIdByEmail(String email) {
        String sql = "SELECT userID FROM users WHERE email = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, email)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(-1)
        );
    }
}