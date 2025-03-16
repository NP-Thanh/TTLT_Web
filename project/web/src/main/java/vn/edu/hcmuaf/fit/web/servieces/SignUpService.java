package vn.edu.hcmuaf.fit.web.servieces;

import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class SignUpService {
    private final UserDao userDao;

    public SignUpService(UserDao userDao) {
        this.userDao = userDao; // Khởi tạo UserDao
    }

    public String registerUser(String name, String email, String password, String phone, String passwordConfirm, HttpSession session) {
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


        session.setAttribute("pendingUser", newUser);
        session.setAttribute("action", "signup"); // Đánh dấu đây là OTP cho đăng ký
        return "Đăng ký thành công, vui lòng xác thực OTP!";
    }

    // Xác nhận OTP và lưu user vào DB
    public boolean confirmOtpAndSaveUser(String email, HttpSession session) {
        User pendingUser = (User) session.getAttribute("pendingUser");

        // Kiểm tra nếu session đã mất user
        if (pendingUser == null || !pendingUser.getEmail().equals(email)) {
            return false;
        }
        boolean isSaved = userDao.addUser(pendingUser);
        if (isSaved) {// Lưu vào DB
            session.removeAttribute("pendingUser"); // Xóa khỏi session
            session.removeAttribute("action");
            return true;
            }
        return false;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
