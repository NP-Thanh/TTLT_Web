package vn.edu.hcmuaf.fit.web.servieces;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;

public class ResetPasswordService {
    private UserDao userDao;

    public ResetPasswordService() {
        userDao = new UserDao(); // Khởi tạo UserDao
    }

    public boolean resetPassword(String email, String oldPassword, String newPassword) {
        User user = userDao.findUserByEmail(email);
        if (user != null && BCrypt.checkpw(oldPassword, user.getPassword())) {
            String hashedNewPassword = hashPassword(newPassword);
            return userDao.setPassword(hashedNewPassword, email);
        }
        return false; // Mật khẩu cũ không đúng
    }

    public boolean setNewPassword(String email, String newPassword) {
        User user = userDao.findUserByEmail(email);
        if (user != null) {
            String hashedNewPassword = hashPassword(newPassword);
            return userDao.setPassword(hashedNewPassword, email);
        }
        return false;
    }


    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}