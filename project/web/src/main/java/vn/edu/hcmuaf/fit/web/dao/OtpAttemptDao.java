package vn.edu.hcmuaf.fit.web.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.OtpAttempt;

import java.util.Optional;

public class OtpAttemptDao {
    private final Jdbi jdbi;

    public OtpAttemptDao(Jdbi jdbi) {
        this.jdbi = JDBIConnector.getJdbi();
    }

    public Optional<OtpAttempt> getOtpAttempt(String email) {
        String sql = "SELECT * FROM otp_attempts WHERE email = :email LIMIT 1";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("email", email)
                        .mapToBean(OtpAttempt.class)
                        .findOne()
        );
    }

    public void insertOtpAttempt(String email) {
        String sql = "INSERT INTO otp_attempts (email, failed_attempts, lock_time) VALUES (:email, 1, NULL)" +
                "ON DUPLICATE KEY UPDATE " + "failed_attempts = failed_attempts + 1, " +
                "lock_time = CASE WHEN failed_attempts >= 5 THEN NOW() ELSE lock_time END"
                ;
        jdbi.useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("email", email)
                        .execute()
                );
    }

    public void lockOtp(String email) {
        String sql = "UPDATE otp_attempts SET lock_time = NOW() WHERE email = :email";
        jdbi.useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("email", email)
                        .execute()
        );
    }

    public void resetOtpAttempt(String email) {
        String sql = "UPDATE otp_attempts SET failed_attempts = 0, lock_time = NULL WHERE email = :email";
        jdbi.useHandle(handle ->
                handle.createUpdate(sql)
                        .bind("email", email)
                        .execute()
        );
    }
}
