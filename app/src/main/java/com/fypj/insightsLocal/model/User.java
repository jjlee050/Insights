package com.fypj.insightsLocal.model;

/**
 * Created by L33525 on 8/10/2014.
 */
public class User {
    private String nric;
    private String name;
    private int age;
    private String contactNo;
    private String address;

    public User() {
    }

    public User(String nric, String name, int age, String contactNo, String address) {
        this.nric = nric;
        this.name = name;
        this.age = age;
        this.contactNo = contactNo;
        this.address = address;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
