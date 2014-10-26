package com.fypj.mymodule.api.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by L33525 on 8/10/2014.
 */
@Entity
public class User_Subsidies {
    @Id
    private String nric;
    @Id
    private Long subsidiesID;
    private Float balance;

    public User_Subsidies() {
    }

    public User_Subsidies(String nric, Long subsidiesID, Float balance) {
        this.nric = nric;
        this.subsidiesID = subsidiesID;
        this.balance = balance;
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

    public void setSubsidiesID(Long subsidies) {
        this.subsidiesID = subsidiesID;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
