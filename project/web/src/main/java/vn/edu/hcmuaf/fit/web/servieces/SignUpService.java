package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;

public class SignUpService {
    private UserDao userDao;

    public SignUpService() {
        userDao = new UserDao(); // Khởi tạo UserDao
    }

    public String registerUser(String name, String email, String password, String phone, String passwordConfirm) {
        // Kiểm tra xem mật khẩu có khớp không
        if (!password.equals(passwordConfirm)) {
            return "Mật khẩu không khớp!";
        }

        // Kiểm tra xem người dùng đã tồn tại chưa
        if (userDao.findUserByEmail(email) != null) {
            return "Email đã tồn tại!";
        }

        // Tạo đối tượng User
        User newUser = new User();
        newUser.setName(name);
        newUser.setRole_id(2); // Giả sử vai trò mặc định là 1
        newUser.setEmail(email);
        newUser.setPassword(hashPassword(password)); // Mã hóa mật khẩu trước khi lưu
        newUser.setPhone(phone);
        newUser.setRegis_date();
        newUser.setStatus("Hoạt động"); // Trạng thái mặc định

        // Thêm người dùng mới vào DB
        if (userDao.addUser(newUser)) {
            return "Đăng ký thành công!";
        } else {
            return "Đăng ký thất bại, vui lòng thử lại.";
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}