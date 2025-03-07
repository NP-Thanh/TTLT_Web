package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {
    private UserDao userDao;

    public LoginService() {
        userDao = new UserDao();
    }

    public boolean validateUser(String email, String password) {
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            // So sánh mật khẩu đã mã hóa với mật khẩu người dùng nhập vào
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }
    public int getID(String email) {
      return userDao.getUserIdByEmail(email);
    }
}