package com.sams.samsqr;

public class AttendanceLog {
    private int timestamp;
    private String fullname;
    private int id;


    public AttendanceLog(int id, int timestamp, String fullname){
        this.timestamp = timestamp;
        this.fullname = fullname;
        this.id = id;
    }

    public AttendanceLog(int timestamp, String fullname){
        this.timestamp = timestamp;
        this.fullname = fullname;
    }

    public AttendanceLog(String fullname){
        this.fullname = fullname;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public int getTimestamp() {
        return timestamp;
    }
    public String getTimestamp(String format) {
        return (String.valueOf(timestamp));
    }

    public int getId() {
        return id;
    }
}
