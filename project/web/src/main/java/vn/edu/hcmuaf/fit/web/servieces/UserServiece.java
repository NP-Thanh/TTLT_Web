package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.RoleDao;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.Role;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.model.UserAdmin;

import java.util.ArrayList;
import java.util.List;

public class UserServiece {
    static UserDao userDao = new UserDao();
    static RoleDao roleDao = new RoleDao();

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public List<UserAdmin> getAllUserWithRole() {
        List<User> users = getAllUsers();
        List<UserAdmin> userAdmins = new ArrayList<>();
        for (User user : users) {
            Role role= roleDao.getRoleById(user.getRole_id());
            UserAdmin userAdmin = new UserAdmin(user, role);
            userAdmins.add(userAdmin);
        }
        return userAdmins;
    }

    public boolean addUser(User user) {
        return userDao.addUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDao.deleteUser(userId, "restricted");
    }
}
