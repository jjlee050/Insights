package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class User {
    @Id
    private String nric;
    private String name;
    private String password;
    private Integer age;
    private String contactNo;
    private String address;
    private Double houseHoldMonthlyIncome;
    private Double annualValueOfResidence;

    public User() {
    }

    public User(String nric, String name, String password, Integer age, String contactNo, String address, Double houseHoldMonthlyIncome, Double annualValueOfResidence) {
        this.nric = nric;
        this.name = name;
        this.password = password;
        this.age = age;
        this.contactNo = contactNo;
        this.address = address;
        this.houseHoldMonthlyIncome = houseHoldMonthlyIncome;
        this.annualValueOfResidence = annualValueOfResidence;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
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

    public Double getHouseHoldMonthlyIncome() {
        return houseHoldMonthlyIncome;
    }

    public void setHouseHoldMonthlyIncome(Double houseHoldMonthlyIncome) {
        this.houseHoldMonthlyIncome = houseHoldMonthlyIncome;
    }

    public Double getAnnualValueOfResidence() {
        return annualValueOfResidence;
    }

    public void setAnnualValueOfResidence(Double annualValueOfResidence) {
        this.annualValueOfResidence = annualValueOfResidence;
    }
}
