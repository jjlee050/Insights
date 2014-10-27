package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class UserSubsidies {
    @Id
    private Long userSubsidiesID;
    private String nric;
    private Long subsidiesID;
    private Float balance;

    public UserSubsidies() {
    }

    public UserSubsidies(Long userSubsidiesID, String nric, Long subsidiesID, Float balance) {
        this.userSubsidiesID = userSubsidiesID;
        this.nric = nric;
        this.subsidiesID = subsidiesID;
        this.balance = balance;
    }

    public Long getUserSubsidiesID() {
        return userSubsidiesID;
    }

    public void setUserSubsidiesID(Long userSubsidiesID) {
        this.userSubsidiesID = userSubsidiesID;
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public Long getSubsidiesID() {
        return subsidiesID;
    }

    public void setSubsidiesID(Long subsidiesID) {
        this.subsidiesID = subsidiesID;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
