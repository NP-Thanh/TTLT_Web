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
        String sql = "INSERT INTO log_entry (level, time, action, who, before_data, after_data) VALUES (?, ?, ?, ?, ?, ?)";
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

    public List<LogEntry> filterLogs(String adminId, String level) {
        return JDBIConnector.getJdbi().withHandle(handle ->
                handle.createQuery("SELECT * FROM log_entry WHERE (:adminId IS NULL OR who = :adminId) " +
                                "AND (:level IS NULL OR level = :level)")
                        .bind("adminId", (adminId.isEmpty()) ? null : adminId)
                        .bind("level", (level.isEmpty()) ? null : level)
                        .mapToBean(LogEntry.class)
                        .list()
        );
    }
}