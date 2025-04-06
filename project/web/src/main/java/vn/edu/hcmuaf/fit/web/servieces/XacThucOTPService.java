package vn.edu.hcmuaf.fit.web.servieces;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.OtpAttemptDao;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.OtpAttempt;

import java.util.*;

public class XacThucOTPService {
    private final OtpAttemptDao otpAttemptDao;
    private static final long OTP_EXPIRATION_TIME = 5 * 60 * 1000; // giới hạn otp 5 phút
    private static final int MAX_ATTEMPTS = 5;   //Sô lần nhập sai
    private static final long LOCK_TIME = 5 * 60 * 1000;  //Khóa 5 phút

    public XacThucOTPService() {
        this.otpAttemptDao = new OtpAttemptDao(JDBIConnector.getJdbi());
    }

    public String otp(String email, HttpSession session) {

        if (isLocked(email)) {
            return "Bạn đã nhập sai OTP quá nhiều lần, vui lòng thử lại sau 5 phút.";
        }

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

    public boolean verifyOtp(HttpSession session, String otp, String email) {
        if (isLocked(email)) {
            return false;
        }

        String storedOtp = (String)  session.getAttribute("otpCode");
        Long expiry = (Long) session.getAttribute("otpExpiry"); // Otp hết hạn
        String action = (String) session.getAttribute("action"); // Kiểm tra loại OTP

        if (storedOtp == null || expiry == null || System.currentTimeMillis() > expiry || action == null) {
            session.removeAttribute("otpCode");
            session.removeAttribute("otpExpiry");
            session.removeAttribute("action"); // Xóa action nếu OTP hết hạn
            otpAttemptDao.resetOtpAttempt(email);
            return false;
        }

        if (storedOtp.equals(otp)) {
            session.removeAttribute("otpCode");
            session.removeAttribute("otpExpiry");
            otpAttemptDao.resetOtpAttempt(email);
            return true;
        } else {
            otpAttemptDao.insertOtpAttempt(email);

            // Kiểm ta số lần nhập sai
            Optional<OtpAttempt> otpAttempt = otpAttemptDao.getOtpAttempt(email);
            if (otpAttempt.isPresent() && otpAttempt.get().getFailedAttempts() >= MAX_ATTEMPTS) {
                otpAttemptDao.lockOtp(email);
                return false;
            }
            return false;
        }
    }

    public String resendOtp(String email, HttpSession session) {
        // Kiểm tra tài khoản có đang bị khóa hay không
        if (isLocked(email)) {
            return "Bạn đã nhập sai OTP quá nhiều lần, vui lòng thử lại sau 5 phút.";
        }
        // Gửi OTP mới
        return otp(email, session);
    }

    public boolean isLocked(String email) {
        Optional<OtpAttempt> otpAttempt = otpAttemptDao.getOtpAttempt(email);
        if (otpAttempt.isPresent()) {
            Date lockTime = otpAttempt.get().getLockTime();
            if (lockTime != null) {
                long lockDuration = System.currentTimeMillis() - lockTime.getTime();
                if (lockDuration < LOCK_TIME) {
                    return true; // Tài khoản đang bị khóa
                } else {
                    otpAttemptDao.resetOtpAttempt(email); // Mở khóa nếu quá thời gian
                    return false; // Tài khoản không bị khóa
                }
            }
        }
        return false; // Tài khoản không tìm thấy, không bị khóa
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

    public void resetOtpAttempt(String email) {
        otpAttemptDao.resetOtpAttempt(email);
    }

    public void insertOtpAttempt(String email) {
        otpAttemptDao.insertOtpAttempt(email);
    }

    public Optional<OtpAttempt> getOtpAttempt(String email) {
        return otpAttemptDao.getOtpAttempt(email);
    }

    public void lockOtp(String email) {
        otpAttemptDao.lockOtp(email);
    }
}
