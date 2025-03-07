package vn.edu.hcmuaf.fit.web.mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailServiece implements IJavaMail {
    @Override
    public boolean send(String to, String subject, String body) {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", PropertyMail.HOST_NAME);
        props.put("mail.smtp.socketFactory.port", PropertyMail.SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Đảm bảo vẫn dùng SSLSocketFactory từ javax
        props.put("mail.smtp.port", PropertyMail.SSL_PORT);

        // get Session
        Session session = Session.getDefaultInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(PropertyMail.APP_EMAIL, PropertyMail.APP_PASSWORD);
            }
        });

        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            // Tạo MimeBodyPart để chứa nội dung email
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(body, "text/plain; charset=UTF-8");

            // Tạo Multipart để kết hợp các phần nội dung
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            // Đặt nội dung của message là Multipart
            message.setContent(multipart);

            // send message
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
