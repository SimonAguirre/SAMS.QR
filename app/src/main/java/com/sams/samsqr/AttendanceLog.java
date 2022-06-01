package com.sams.samsqr;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AttendanceLog {
    private String timestamp;
    private String fullname;
    private int id;


    public AttendanceLog(int id, String timestamp, String fullname) {
        this.timestamp = timestamp;
        this.fullname = fullname;
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AttendanceLog(String fullname,String timestamp) {
        this.fullname = fullname;
        this.timestamp = timestamp;
    }

    public  String getTime(){;
        Timestamp timestamp2 = Timestamp.valueOf(timestamp);
        String pattern = "hh-mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time = simpleDateFormat.format(timestamp2);
        return time;
    }
}