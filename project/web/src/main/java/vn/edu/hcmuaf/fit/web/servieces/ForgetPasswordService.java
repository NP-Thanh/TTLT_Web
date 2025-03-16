package vn.edu.hcmuaf.fit.web.servieces;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;

import java.util.Properties;
import java.util.UUID;

public class ForgetPasswordService {
    private final UserDao userDao;
    private final XacThucOTPService xacThucOTPService ;


    public ForgetPasswordService() {
        userDao = new UserDao();// Khởi tạo UserDao
        xacThucOTPService = new XacThucOTPService();
    }

    public String sendOTP (String email, HttpSession session) {
        User user = userDao.findUserByEmail(email);
        if (user == null) {
            return "Email không tồn tại.";
        }

        // Gửi OTP qua email
        String otpMessage = xacThucOTPService.otp(email, session);
        if (otpMessage.startsWith("Lỗi")) {
            return otpMessage;
        }
        // Lưu email vào session để dùng ở bước nhập OTP
        session.setAttribute("email", email);
        session.setAttribute("action", "resetPassword");   // Đánh dấu OTP này là cho quên mật khẩu
        return " Mã OTP đã được gửi đến email của bạn. Vui lòng kiểm tra!";
    }
}