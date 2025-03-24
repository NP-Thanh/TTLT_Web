package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.LogEntryDao;
import vn.edu.hcmuaf.fit.web.model.LogEntry;
import java.sql.Timestamp;
import java.util.List;

public class LogEntryService {
    private static LogEntryDao logEntryDao = new LogEntryDao();

    public LogEntry getLogById(int id) {
        return logEntryDao.getLogById(id);
    }

    public List<LogEntry> getAllLogs() {
        return logEntryDao.getAllLogs();
    }

    public void logAction(String level, String action, int who, String beforeData, String afterData) {
        LogEntry log = new LogEntry(0, level, new Timestamp(System.currentTimeMillis()), action, who, beforeData, afterData);
        logEntryDao.insertLog(log);
    }

    public void deleteLog(int id) {
        logEntryDao.deleteLog(id);
    }
}
