package vn.edu.hcmuaf.fit.web.model;

import java.sql.Timestamp;

public class OtpAttempt {
    private int id;
    private String email;
    private int failedAttempts;
    private Timestamp lockTime;
    private String ip;
    public OtpAttempt( String email, int failedAttempts, Timestamp lockTime , String ip) {
        this.email = email;
        this.failedAttempts = failedAttempts;
        this.lockTime = lockTime;
        this.ip = ip;
    }
    public OtpAttempt() {}
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public int getFailedAttempts() {
        return failedAttempts;
    }
    public Timestamp getLockTime() {
        return lockTime;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
    public void setLockTime(Timestamp lockTime) {
        this.lockTime = lockTime;
    }
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
