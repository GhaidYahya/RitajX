package com.example.ritajx;

public class Task {
    private String taskname;
    private String time;
    String taskType;

    private int bookingID;

    public Task(String taskname, String time) {
        this.taskname = taskname;
        this.time = time;
    }

    public Task(String taskname, String time, int bookingID) {
        this.taskname = taskname;
        this.time = time;
        this.bookingID = bookingID;
    }

    public Task(String taskname, String time, String taskType, int bookingID) {
        this.taskname = taskname;
        this.time = time;
        this.taskType = taskType;
        this.bookingID = bookingID;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskname='" + taskname + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
