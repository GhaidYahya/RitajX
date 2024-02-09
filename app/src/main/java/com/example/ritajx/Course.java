package com.example.ritajx;

public class Course {
    private String courseID;
    private String days;
    private String time;
    private String roomNumber;

    public Course(String courseID, String days, String time, String roomNumber) {
        this.courseID = courseID;
        this.days = days;
        this.time = time;
        this.roomNumber = roomNumber;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {

        this.courseID = courseID;
    }
    public String getDays() {
        return days;
    }

    public void setDays(String days) {

        this.days = days;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {

        this.roomNumber = roomNumber;
    }

}
