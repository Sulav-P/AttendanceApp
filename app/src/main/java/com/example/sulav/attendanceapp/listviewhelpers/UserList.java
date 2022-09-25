package com.example.sulav.attendanceapp.listviewhelpers;

public class UserList {

    private String ROLL_NO;
    private String STUDENT_NAME;
    private String STATUS;

    public UserList() {
    }

    public UserList(String ROLL_NO, String STUDENT_NAME, String STATUS) {
        this.ROLL_NO = ROLL_NO;
        this.STUDENT_NAME = STUDENT_NAME;
        this.STATUS = STATUS;
    }

    public String getROLL_NO() {
        return ROLL_NO;
    }

    public void setROLL_NO(String ROLL_NO) {
        this.ROLL_NO = ROLL_NO;
    }

    public String getSTUDENT_NAME() {
        return STUDENT_NAME;
    }

    public void setSTUDENT_NAME(String STUDENT_NAME) {
        this.STUDENT_NAME = STUDENT_NAME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
