package com.hospital.models;

public class Nurse extends User {
    private String nurseId;
    private String department;

    public Nurse(String username, String password, String email, String nurseId, String department) {
        super(username, password, email, "NURSE");
        this.nurseId = nurseId;
        this.department = department;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}