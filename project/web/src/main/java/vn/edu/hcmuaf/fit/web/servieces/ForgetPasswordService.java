package vn.edu.hcmuaf.fit.web.servieces;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;

import java.util.Properties;
import java.util.UUID;

public class ForgetPasswordService {
    private UserDao userDao;


    public ForgetPasswordService() {
        userDao = new UserDao(); // Khởi tạo UserDao
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String resetPassword(String email) {
        User user = userDao.findUserByEmail(email);
        if (user == null) {
            return "Email không tồn tại.";
        }

        // Tạo mật khẩu mới
        String newPassword = UUID.randomUUID().toString().substring(0, 8); // Mật khẩu ngẫu nhiên



        userDao.setPassword(hashPassword(newPassword), email); // Cập nhật mật khẩu mới vào cơ sở dữ liệu

        // Gửi email với mật khẩu mới
        String subject = "Mật khẩu mới của bạn";
        String content = "Mật khẩu mới của bạn là: " + newPassword;

        try {
            sendEmail(email, subject, content);
            return "Mật khẩu mới đã được gửi đến email của bạn.";
        } catch (MessagingException e) {
            e.printStackTrace(); // Ghi log lỗi
            return "Gửi email thất bại: " + e.getMessage(); // Thông báo lỗi cho người dùng
        }
    }

    //    private void sendEmail(String to, String subject, String content) throws MessagingException {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                // Thay thế dưới đây bằng tên người dùng và mật khẩu email của bạn
//                return new PasswordAuthentication(" nguyenthanhson180804@gmail.com", "crmd hzwn katk zohl");
//            }
//        });
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress("xhoang345@gmail.com")); // Thay đổi địa chỉ email nếu cần
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//        message.setSubject(subject);
//        message.setText(content);
//
//        Transport.send(message);
//    }
    private void sendEmail(String to, String subject, String content) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");  // Đổi sang port TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nguyenthanhson180804@gmail.com", "crmd hzwn katk zohl");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nguyenthanhson180804@gmail.com")); // Sử dụng cùng email với username
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("Không thể gửi email: " + e.getMessage(), e);
        }
    }
}