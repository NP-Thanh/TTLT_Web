package vn.edu.hcmuaf.fit.web.mail;

public interface IJavaMail {
    public boolean send(String to, String subject, String body);
}
