package com.example.sulav.attendanceapp.listviewhelpers;

public class IndividualList {
    private String ROLL_NO;
    private String STATUS;
    private String STUDENT_NAME;

    public IndividualList() {
    }

    public IndividualList(String ROLL_NO, String STATUS, String STUDENT_NAME) {
        this.ROLL_NO = ROLL_NO;
        this.STATUS = STATUS;
        this.STUDENT_NAME = STUDENT_NAME;
    }

    public String getROLL_NO() {
        return ROLL_NO;
    }

    public void setROLL_NO(String ROLL_NO) {
        this.ROLL_NO = ROLL_NO;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getSTUDENT_NAME() {
        return STUDENT_NAME;
    }

    public void setSTUDENT_NAME(String STUDENT_NAME) {
        this.STUDENT_NAME = STUDENT_NAME;
    }
}

