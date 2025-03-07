package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;

public class UpdateProfileService {
    private UserDao userDao;

    public UpdateProfileService() {
        userDao = new UserDao(); // Khởi tạo UserDao
    }

    public boolean updateUserProfile(User user) {
        return userDao.updateUser(user); // Giả sử có phương thức updateUser trong UserDao
    }
}