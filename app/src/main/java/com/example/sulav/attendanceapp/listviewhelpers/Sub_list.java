package com.example.sulav.attendanceapp.listviewhelpers;

public class Sub_list {
    private String COURSE_NAME;
    private String CLASS_NAME;

    public Sub_list() {
    }

    public Sub_list(String COURSE_NAME, String CLASS_NAME) {
        this.COURSE_NAME = COURSE_NAME;
        this.CLASS_NAME = CLASS_NAME;
    }

    public String getCOURSE_NAME() {
        return COURSE_NAME;
    }

    public void setCOURSE_NAME(String COURSE_NAME) {
        this.COURSE_NAME = COURSE_NAME;
    }

    public String getCLASS_NAME() {
        return CLASS_NAME;
    }

    public void setCLASS_NAME(String CLASS_NAME) {
        this.CLASS_NAME = CLASS_NAME;
    }
}
