package com.example.sulav.attendanceapp;

public class StudentCheck {

    private String Student_Email_ID;
    private String Student_Name;

    public StudentCheck() {
    }

    public StudentCheck(String student_Email_ID, String student_Name) {
        Student_Email_ID = student_Email_ID;
        Student_Name = student_Name;
    }

    public String getStudent_Email_ID() {
        return Student_Email_ID;
    }

    public void setStudent_Email_ID(String student_Email_ID) {
        Student_Email_ID = student_Email_ID;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }
}

