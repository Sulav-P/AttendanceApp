package com.example.sulav.attendanceapp;

public class ParentCheck {
    private String Parent_Name;
    private String Parent_Email_ID;

    public ParentCheck() {
    }

    public ParentCheck(String parent_Name, String parent_Email_ID) {
        Parent_Name = parent_Name;
        Parent_Email_ID = parent_Email_ID;
    }

    public String getParent_Name() {
        return Parent_Name;
    }

    public void setParent_Name(String parent_Name) {
        Parent_Name = parent_Name;
    }

    public String getParent_Email_ID() {
        return Parent_Email_ID;
    }

    public void setParent_Email_ID(String parent_Email_ID) {
        Parent_Email_ID = parent_Email_ID;
    }
}
