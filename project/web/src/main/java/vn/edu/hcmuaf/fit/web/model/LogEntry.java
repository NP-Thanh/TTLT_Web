package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogEntry implements Serializable {
    private int id;
    private String level;
    private Timestamp time;
    private String action;
    private int who;
    private String beforeData;
    private String afterData;

    public LogEntry() {
    }

    public LogEntry(int id, String level, Timestamp time, String action, int who, String beforeData, String afterData) {
        this.id = id;
        this.level = level;
        this.time = time;
        this.action = action;
        this.who = who;
        this.beforeData = beforeData;
        this.afterData = afterData;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public Timestamp getTime() { return time; }
    public void setTime(Timestamp time) { this.time = time; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public int getWho() { return who; }
    public void setWho(int who) { this.who = who; }

    public String getBeforeData() { return beforeData; }
    public void setBeforeData(String beforeData) { this.beforeData = beforeData; }

    public String getAfterData() { return afterData; }
    public void setAfterData(String afterData) { this.afterData = afterData; }
}