package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.LogEntry;
import java.util.List;

public class LogEntryDao {
    public LogEntry getLogById(int id) {
        return JDBIConnector.getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM log_entry WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(LogEntry.class)
                        .findOne().orElse(null)
        );
    }

    public List<LogEntry> getAllLogs() {
        return JDBIConnector.getJdbi().withHandle(h ->
                h.createQuery("SELECT * FROM log_entry ORDER BY time DESC")
                        .mapToBean(LogEntry.class)
                        .list()
        );
    }

    public void insertLog(LogEntry log) {
        String sql = "INSERT INTO log_entry (level, time, action, who, beforeData, afterData) VALUES (?, ?, ?, ?, ?, ?)";
        JDBIConnector.getJdbi().useHandle(h ->
                h.createUpdate(sql)
                        .bind(0, log.getLevel())
                        .bind(1, log.getTime())
                        .bind(2, log.getAction())
                        .bind(3, log.getWho())
                        .bind(4, log.getBeforeData())
                        .bind(5, log.getAfterData())
                        .execute()
        );
    }

    public void deleteLog(int id) {
        JDBIConnector.getJdbi().useHandle(h ->
                h.createUpdate("DELETE FROM log_entry WHERE id = ?")
                        .bind(0, id)
                        .execute()
        );
    }
}