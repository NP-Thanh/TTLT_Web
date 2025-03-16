package vn.edu.hcmuaf.fit.web.servieces;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class XacThucOTPService {
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút

    public String otp(String email, HttpSession session) {
        String otpCode = getRandomOTP();
        long expirationTime = System.currentTimeMillis() + OTP_EXPIRATION_TIME;

        session.setAttribute("otpCode", otpCode);
        session.setAttribute("otpExpiry", expirationTime);

        String subject = "[Bảo mật] Mã OTP xác thực tài khoản của bạn";
        String content = "Mã OTP của bạn là: " + otpCode + " có hiệu lực trong 5 phút";


//        Gửi Email bất đồng bộ ở luồng riêng
        new Thread(() -> {
            try {
                sendEmail(email, subject, content);
            } catch (MessagingException e) {
                System.err.println("Lỗi khi gửi email OTP: " + e.getMessage());
            }
        }).start();

        return "Mã OTP đã được gửi đến email của bạn";
    }

    public boolean verifyOtp(HttpSession session, String otp) {
        String storedOtp = (String)  session.getAttribute("otpCode");
        Long expiry = (Long) session.getAttribute("otpExpiry");

        if (storedOtp == null || expiry == null || System.currentTimeMillis() > expiry) {
            session.removeAttribute("otpCode");
            session.removeAttribute("otpExpiry");
            return false;
        }

        if (storedOtp.equals(otp)) {
            session.removeAttribute("otpCode");
            session.removeAttribute("otpExpiry");
            return true;
        } else {
            return false;
        }
    }

    public String getRandomOTP(){
        return String.format("%06d", new Random().nextInt(1000000));

    }

    private Session getEmailSession(){
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");  // Đổi sang port TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Kiểm tra biến môi trường trước khi dùng
        String emailUsername = System.getenv("EMAIL_USERNAME");
        String emailPassword = System.getenv("EMAIL_PASSWORD");

        return Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                       emailUsername, emailPassword
                );
            }
        });
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        try{
            Session session = getEmailSession();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(System.getenv("EMAIL_USERNAME"))); // Sử dụng cùng email với username
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }

    private static class OtpEntry {
        String otpCode;
        long expiryTime;

        public OtpEntry(String otpCode, long expiryTime) {
            this.otpCode = otpCode;
            this.expiryTime = expiryTime;
        }
    }
}
