package com.example.sulav.attendanceapp.listviewhelpers;

public class User {

    private String Student_Name;
    private String Student_RollNo;


    public User() {
    }

    public User(String student_Name, String student_RollNo) {
        Student_Name = student_Name;
        Student_RollNo = student_RollNo;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getStudent_RollNo() {
        return Student_RollNo;
    }

    public void setStudent_RollNo(String student_RollNo) {
        Student_RollNo = student_RollNo;
    }
}
