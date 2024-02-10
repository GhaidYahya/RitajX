package com.example.ritajx;

public class GradeObject {
    private String courseName;
    private String gradeType;
    private String gradeValue; // Assuming it's a String for simplicity in displaying, can be parsed as needed
    private String gradeDescription;

    public GradeObject(String courseName, String gradeType, String gradeValue, String gradeDescription) {
        this.courseName = courseName;
        this.gradeType = gradeType;
        this.gradeValue = gradeValue;
        this.gradeDescription = gradeDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType;
    }

    public String getGradeValue() {
        return gradeValue;
    }

    public void setGradeValue(String gradeValue) {
        this.gradeValue = gradeValue;
    }

    public String getGradeDescription() {
        return gradeDescription;
    }

    public void setGradeDescription(String gradeDescription) {
        this.gradeDescription = gradeDescription;
    }
}


