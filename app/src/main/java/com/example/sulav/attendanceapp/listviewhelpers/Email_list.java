package com.example.sulav.attendanceapp.listviewhelpers;

public class Email_list {
    private String Teacher_Name;
    private String Teacher_Email_ID;

    public Email_list() {
    }

    public Email_list(String teacher_Name, String teacher_Email_ID) {
        Teacher_Name = teacher_Name;
        Teacher_Email_ID = teacher_Email_ID;
    }

    public String getTeacher_Name() {
        return Teacher_Name;
    }

    public void setTeacher_Name(String teacher_Name) {
        Teacher_Name = teacher_Name;
    }

    public String getTeacher_Email_ID() {
        return Teacher_Email_ID;
    }

    public void setTeacher_Email_ID(String teacher_Email_ID) {
        Teacher_Email_ID = teacher_Email_ID;
    }
}
