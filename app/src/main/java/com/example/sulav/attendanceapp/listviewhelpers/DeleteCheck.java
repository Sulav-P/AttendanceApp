package com.example.sulav.attendanceapp.listviewhelpers;

public class DeleteCheck {
    private String Semester_Name;
    private String Class_Name;
    private String Course_Name;
    private String Teacher_Name;
    private String Teacher_Email;
    private String Key;

    public DeleteCheck() {
    }

    public DeleteCheck(String semester_Name, String class_Name, String course_Name, String teacher_Name, String teacher_Email, String key) {
        Semester_Name = semester_Name;
        Class_Name = class_Name;
        Course_Name = course_Name;
        Teacher_Name = teacher_Name;
        Teacher_Email = teacher_Email;
        Key = key;
    }

    public String getSemester_Name() {
        return Semester_Name;
    }

    public void setSemester_Name(String semester_Name) {
        Semester_Name = semester_Name;
    }

    public String getClass_Name() {
        return Class_Name;
    }

    public void setClass_Name(String class_Name) {
        Class_Name = class_Name;
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public void setCourse_Name(String course_Name) {
        Course_Name = course_Name;
    }

    public String getTeacher_Name() {
        return Teacher_Name;
    }

    public void setTeacher_Name(String teacher_Name) {
        Teacher_Name = teacher_Name;
    }

    public String getTeacher_Email() {
        return Teacher_Email;
    }

    public void setTeacher_Email(String teacher_Email) {
        Teacher_Email = teacher_Email;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
