package com.example.ritajx;

public class Task {
    private String taskname;
    private String time;

    public Task(String taskname, String time) {
        this.taskname = taskname;
        this.time = time;
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
}
