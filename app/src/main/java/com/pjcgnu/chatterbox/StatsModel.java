package com.pjcgnu.chatterbox;

import java.time.LocalDateTime;

public class StatsModel {
    private int id;
    private String name;
    private String s_time;
    private String e_time;
    private String t_time;

    public StatsModel(int id, String s_time, String e_time, String t_time, String name) {
        this.id = id;
        this.name = name;
        this.s_time = s_time;
        this.e_time = e_time;
        this.t_time = t_time;
    }

    public int getReadingID() { return id; }
    public void setReadingID(int id) {
        this.id = id;
    }
    public String getReadingName() {
        return name;
    }
    public void setReadingName(String name) {
        this.name = name;
    }

    public String getReadingSTime() {
        return s_time;
    }
    public void setReadingSTime(String s_time) {
        this.s_time = s_time;
    }

    public String getReadingETime() {
        return e_time;
    }
    public void setReadingETime(String e_time) {
        this.e_time = e_time;
    }

    public String getReadingTTime() {
        return t_time;
    }
    public void setReadingTTime(String t_time) {
        this.t_time = t_time;
    }
}
