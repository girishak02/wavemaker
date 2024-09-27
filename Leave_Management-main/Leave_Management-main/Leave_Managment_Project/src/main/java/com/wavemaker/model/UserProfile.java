package com.wavemaker.model;


public class UserProfile {
    private int empId;
    private String empName;
    private String empEmail;
    private String PhoneNumber;
    private String dob;
    private int managerId;

    public UserProfile(int empId, String empName, String empEmail, String PhoneNumber, String dob, int managerId) {
        this.empId = empId;
        this.empName = empName;
        this.empEmail = empEmail;
        this.PhoneNumber = PhoneNumber;
        this.dob = dob;
        this.managerId = managerId;
    }

    public UserProfile() {
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setDob(int doB) {

    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public void setManagerId(int managerId) {
    }

    public int getManagerId() {
        return managerId;
    }
}