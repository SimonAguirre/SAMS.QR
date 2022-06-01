package com.sams.samsqr;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AttendanceLog {
    private Timestamp timestamp;
    private String fullname;
    private int id;


    //When reading a log
    public AttendanceLog(int id, Timestamp timestamp, String fullname) {
        this.timestamp = timestamp;
        this.fullname = fullname;
        this.id = id;
    }

    //When creating  a log
    public AttendanceLog(String fullname,Timestamp timestamp) {
        this.fullname = fullname;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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

    public  String getTime(){;
        String pattern = "hh:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String time = simpleDateFormat.format(timestamp);
        return time;
    }
}