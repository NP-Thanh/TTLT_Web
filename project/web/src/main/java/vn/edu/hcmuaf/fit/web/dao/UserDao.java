package vn.edu.hcmuaf.fit.web.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.User;

import java.util.List;

public class UserDao {
    private Jdbi jdbi = JDBIConnector.getJdbi();

    public boolean setPassword(String newPassword, String email) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, newPassword)
                        .bind(1, email)
                        .execute() > 0 // Trả về true nếu có bản ghi được cập nhật
        );
    }

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

    public boolean addUser(User newUser) {
        String sql = "INSERT INTO users (name, role_id, email, phone, password, status) VALUES (?, ?, ?, ?, ?, ?)";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, newUser.getName())
                        .bind(1, newUser.getRole_id())
                        .bind(2, newUser.getEmail())
                        .bind(3, newUser.getPhone())
                        .bind(4, newUser.getPassword())
                        .bind(5, newUser.getStatus())
                        .execute()
        ) > 0;
    }

    public int getUserIdByEmail(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, email)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(-1)
        );
    }
    public boolean updateUser(User updatedUser) {
        String sql = "UPDATE users SET name = ?, phone = ? WHERE email = ?";
        return jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, updatedUser.getName())
                        .bind(1, updatedUser.getPhone())
                        .bind(2, updatedUser.getEmail())
                        .execute()
        ) > 0;
    }
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, userId)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }
    public List<User> getAllUsers() {
        String sql = "select u.id, u.name, u.email, u.phone, u.`password`, u.regis_date, u.`status`, r.id as role_id, r.role_name from users u\n" +
                "join role r on r.id = u.role_id";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .mapToBean(User.class)
                        .list()
        );
    }

    public void setRole(int roleId, int userId) {
        String query = "update users " +
                "set `role_id` = ? " +
                "WHERE id = ?";

        jdbi.withHandle(handle ->
                handle.createUpdate(query)
                        .bind(0, roleId)
                        .bind(1, userId)
                        .execute()
        );
    }

    public boolean deleteUser(int userId, String status) {
        //status = Hạn chế hoặc Hoạt động
        String query = "update users set status = ? where id = ?";
        return jdbi.withHandle(handle ->
                handle.createUpdate(query)
                        .bind(0, status)
                        .bind(1, userId)
                        .execute()
        ) > 0;

    }
}
